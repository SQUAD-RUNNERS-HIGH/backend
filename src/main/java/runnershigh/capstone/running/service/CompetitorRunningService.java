package runnershigh.capstone.running.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.repository.CourseDocumentRepository;
import runnershigh.capstone.running.domain.UserCoordinate;
import runnershigh.capstone.running.dto.request.CompetitorRunningInfoRequest;
import runnershigh.capstone.running.dto.response.CompetitorRunningResponse;
import runnershigh.capstone.running.dto.RunningStatus;
import runnershigh.capstone.running.geometry.GeometryProjectionHandler;
import runnershigh.capstone.running.geometry.GeometryProjectionHandlerMapping;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompetitorRunningService {

    private final CourseDocumentRepository courseDocumentRepository;
    private final GeometryProjectionHandlerMapping projectionHandlerMapping;

    public CompetitorRunningResponse calculateCompetitorRunning(
        final CompetitorRunningInfoRequest request, final String courseId) {
        log.info("Competitor Running Request {}", request);

        final Document courseDocument = courseDocumentRepository.findByObjectId(new ObjectId(courseId));
        final GeometryProjectionHandler handler = projectionHandlerMapping.getHandler(courseDocument);

        final UserCoordinate rawUserCoordinate = new UserCoordinate(request.longitude(), request.latitude());
        final UserCoordinate projectedUserCoordinate = handler.project(courseDocument, rawUserCoordinate);

        if (projectedUserCoordinate.isUserEscapedCourse(rawUserCoordinate)) {
            return new CompetitorRunningResponse(RunningStatus.ESCAPED, rawUserCoordinate.x, rawUserCoordinate.y);
        }
        return new CompetitorRunningResponse(RunningStatus.ONGOING, projectedUserCoordinate.x,
            projectedUserCoordinate.y);
    }
}
