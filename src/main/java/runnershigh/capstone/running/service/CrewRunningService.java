package runnershigh.capstone.running.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.repository.CourseDocumentRepository;
import runnershigh.capstone.running.domain.UserCoordinate;
import runnershigh.capstone.running.dto.request.CrewParticipantInfoRequest;
import runnershigh.capstone.running.dto.response.CrewParticipantInfoResponse;
import runnershigh.capstone.running.dto.request.CrewRunningInfoRequest;
import runnershigh.capstone.running.dto.response.CrewRunningResponse;
import runnershigh.capstone.running.dto.RunningStatus;
import runnershigh.capstone.running.geometry.GeometryProjectionHandler;
import runnershigh.capstone.running.geometry.GeometryProjectionHandlerMapping;
import runnershigh.capstone.running.repository.CrewRunningRedisRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrewRunningService {

    private static final int MINIMUM_CREW_RUNNING_PARTICIPANTS = 3;
    private final CrewRunningRedisRepository crewRunningRedisRepository;
    private final CourseDocumentRepository courseDocumentRepository;
    private final GeometryProjectionHandlerMapping projectionHandlerMapping;

    public CrewRunningResponse calculateCrewRunning(final CrewRunningInfoRequest request, final String courseId,
        final String crewId) {
        log.info("Crew Running Request {}", request);

        final Document courseDocument = courseDocumentRepository.findByObjectId(new ObjectId(courseId));
        final GeometryProjectionHandler handler = projectionHandlerMapping.getHandler(courseDocument);

        final UserCoordinate rawUserCoordinate = new UserCoordinate(request.longitude(), request.latitude());
        final UserCoordinate projectedUserCoordinate = handler.project(courseDocument, rawUserCoordinate);

        if (projectedUserCoordinate.isUserEscapedCourse(rawUserCoordinate)) {
            return new CrewRunningResponse(RunningStatus.ESCAPED, request.userId(), rawUserCoordinate.x,
                rawUserCoordinate.y);
        }
        return new CrewRunningResponse(RunningStatus.ONGOING, request.userId(), projectedUserCoordinate.x,
            projectedUserCoordinate.y);
    }

    public CrewParticipantInfoResponse sendReadyLocation(final CrewParticipantInfoRequest request,
        final String courseId, final String crewId) {
        crewRunningRedisRepository.addLocation(request, courseId, crewId);
        crewRunningRedisRepository.addReadyStatus(courseId, crewId, request.userId(),
            request.isReady());
        boolean startSignal = isAllCrewWithinStartDistance(request.userId(),
            courseId, crewId);
        return new CrewParticipantInfoResponse(
            request.userId(), request.longitude(),
            request.latitude(), request.isReady(), startSignal);
    }

    private boolean isAllCrewWithinStartDistance(final String userId,
        final String courseId, final String crewId) {
        List<String> userIds = crewRunningRedisRepository.geoSearch(courseId, crewId, userId);
        Map<String, Boolean> readyStatus = crewRunningRedisRepository.getReadyStatus(courseId,
            crewId);
        if (userIds.size() >= MINIMUM_CREW_RUNNING_PARTICIPANTS &&
            userIds.size() == readyStatus.size() && isAllReady(readyStatus, userIds)) {
            return true;
        }
        return false;
    }

    private boolean isAllReady(final Map<String, Boolean> readyStatus, List<String> userIds) {
        for (String userId : userIds) {
            if (Boolean.FALSE.equals(readyStatus.get(userId))) {
                return false;
            }
        }
        return true;
    }
}
