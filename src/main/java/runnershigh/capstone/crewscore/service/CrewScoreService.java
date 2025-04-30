package runnershigh.capstone.crewscore.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.service.CourseService;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.exception.CrewNotFoundException;
import runnershigh.capstone.crew.repository.CrewRepository;
import runnershigh.capstone.crew.service.CrewService;
import runnershigh.capstone.crewscore.domain.CrewScore;
import runnershigh.capstone.crewscore.dto.request.CrewScoreRequest;
import runnershigh.capstone.crewscore.dto.response.CrewRankListResponse;
import runnershigh.capstone.crewscore.dto.response.CrewRankListResponse.CrewRankResponse;
import runnershigh.capstone.crewscore.exception.CrewScoreNotFound;
import runnershigh.capstone.crewscore.repository.CrewScoreRepository;
import runnershigh.capstone.global.error.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewScoreService {

    private final CrewScoreRepository crewScoreRepository;
    private final CourseService courseService;
    private final CrewRepository crewRepository;
    private final RedisTemplate<String,Object> redisTemplate;
    private final static String RANK_KEY = "crew:rank";

    @Transactional
    public void updateCrewScore(final CrewScoreRequest request){
        final Course course = courseService.getCourse(request.courseId());
        CrewScore crewScore = getCrewScore(request.crewId());
        crewScore.updateScore(request.numberOfRunners(),course.getProperties().getPerimeter());
        redisTemplate.opsForZSet().add(RANK_KEY,request.crewId().toString(),crewScore.getScore());
    }

    @Transactional
    public void save(final Crew crew){
        final CrewScore crewScore = new CrewScore(crew);
        redisTemplate.opsForZSet().add(RANK_KEY,crew.getId().toString(),0);
        crewScoreRepository.save(crewScore);
    }

    public CrewRankListResponse getRanks(final Long size){
        List<TypedTuple<Object>> collect = redisTemplate.opsForZSet()
            .reverseRangeWithScores(RANK_KEY, 0, size)
            .stream()
            .toList();
        List<CrewRankResponse> crewRanks = collect.stream()
            .map(u -> {
                Crew crew = crewRepository.findById(Long.parseLong((String) u.getValue()))
                    .orElseThrow(()->new CrewNotFoundException(ErrorCode.CREW_NOT_FOUND));
                return new CrewRankResponse((String) u.getValue(), u.getScore(),
                    crew.getImage(),crew.getName());
            }).collect(Collectors.toList());
        return new CrewRankListResponse(crewRanks);
    }

    public CrewScore getCrewScore(final Long crewId){
        return crewScoreRepository.findByCrewId(crewId)
            .orElseThrow(() -> new CrewScoreNotFound(ErrorCode.CREW_SCORE_NOT_FOUND));
    }

    public long getCrewRank(final Long crewId){
        Long rank = redisTemplate.opsForZSet().reverseRank(RANK_KEY, crewId.toString());
        return rank + 1;
    }
}
