package runnershigh.capstone.crewscore.service;

import lombok.RequiredArgsConstructor;
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

    @Transactional
    public void updateCrewScore(final CrewScoreRequest request){
        final Course course = courseService.getCourse(request.courseId());
        CrewScore crewScore = getCrewScore(request.crewId());
        crewScore.updateScore(request.numberOfRunners(),course.getProperties().getPerimeter());
    }

    @Transactional
    public void save(final Crew crew){
        final CrewScore crewScore = new CrewScore(crew);
        crewScoreRepository.save(crewScore);
    }

    public CrewScore getCrewScore(final Long crewId){
        return crewScoreRepository.findByCrewId(crewId)
            .orElseThrow(() -> new CrewScoreNotFound(ErrorCode.CREW_SCORE_NOT_FOUND));
    }
}
