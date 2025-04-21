package runnershigh.capstone.crewscore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.service.CourseService;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crewscore.domain.CrewScore;
import runnershigh.capstone.crewscore.dto.request.CrewScoreRequest;
import runnershigh.capstone.crewscore.exception.CrewScoreNotFound;
import runnershigh.capstone.crewscore.repository.CrewScoreRepository;
import runnershigh.capstone.global.error.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewScoreService {

    private final CrewScoreRepository crewScoreRepository;
    private final CourseService courseService;
    private final RedisTemplate<String,Object> redisTemplate;
    private final static String RANK_KEY = "crew:rank";

    @Transactional
    public void updateCrewScore(final CrewScoreRequest request){
        final Course course = courseService.getCourse(request.courseId());
        CrewScore crewScore = getCrewScore(request.crewId());
        crewScore.updateScore(request.numberOfRunners(),course.getProperties().getPerimeter());
        redisTemplate.opsForZSet().add(RANK_KEY,request.crewId(),crewScore.getScore());
    }

    @Transactional
    public void save(final Crew crew){
        final CrewScore crewScore = new CrewScore(crew);
        redisTemplate.opsForZSet().add(RANK_KEY,crew.getId(),0);
        crewScoreRepository.save(crewScore);
    }

    public CrewScore getCrewScore(final Long crewId){
        return crewScoreRepository.findByCrewId(crewId)
            .orElseThrow(() -> new CrewScoreNotFound(ErrorCode.CREW_SCORE_NOT_FOUND));
    }

    public long getCrewRank(final Long crewId){
        Long rank = redisTemplate.opsForZSet().reverseRank(RANK_KEY, crewId);
        return rank + 1; // 1등부터 시작
    }
}
