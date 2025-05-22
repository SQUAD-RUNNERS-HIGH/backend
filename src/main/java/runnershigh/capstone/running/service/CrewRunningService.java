package runnershigh.capstone.running.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.repository.CourseDocumentRepository;
import runnershigh.capstone.running.domain.UserCoordinate;
import runnershigh.capstone.running.dto.request.CrewParticipantInfoRequest;
import runnershigh.capstone.running.dto.response.CrewParticipantInfoResponse;
import runnershigh.capstone.running.dto.request.CrewRunningInfoRequest;
import runnershigh.capstone.running.dto.response.CrewParticipantInfoResponse.CrewParticipantInfo;
import runnershigh.capstone.running.dto.response.CrewRunningResponse;
import runnershigh.capstone.running.dto.RunningStatus;
import runnershigh.capstone.running.geometry.GeometryProjectionHandler;
import runnershigh.capstone.running.geometry.GeometryProjectionHandlerMapping;
import runnershigh.capstone.running.repository.CrewRunningRedisRepository;
import runnershigh.capstone.running.repository.CrewRunningRedisRepository.ParticipantLocation;
import runnershigh.capstone.running.repository.CrewRunningRedisRepository.ReadyStatus;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrewRunningService {

    private final CrewRunningRedisRepository crewRunningRedisRepository;
    private final CourseDocumentRepository courseDocumentRepository;
    private final GeometryProjectionHandlerMapping projectionHandlerMapping;

    public CrewRunningResponse calculateCrewRunning(final CrewRunningInfoRequest request, final String courseId,
        final String crewId) {
        log.info("Crew Running Request {}", request);

        final Document courseDocument = courseDocumentRepository.findByObjectId(new ObjectId(courseId));
        final GeometryProjectionHandler handler = projectionHandlerMapping.getHandler(courseDocument);

        final Coordinate rawUserCoordinate = new UserCoordinate(request.longitude(), request.latitude());
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
            request.isReady(),request.username());
        return new CrewParticipantInfoResponse(getCrewParticipantInfo(request.userId(),courseId,crewId));
    }

    private List<CrewParticipantInfo> getCrewParticipantInfo(final String userId,
        final String courseId, final String crewId) {
        List<ParticipantLocation> locations = crewRunningRedisRepository.geoSearch(courseId, crewId, userId);
        Map<String, ReadyStatus> readyStatus = crewRunningRedisRepository.getReadyStatus(courseId,
            crewId);
        return locations.stream().map(l -> {
            ReadyStatus status = readyStatus.get(l.getUserId());
            return new CrewParticipantInfo(l.getUserId(), status.isReady(),
                status.getUsername(), l.getLongitude(),
                l.getLatitude());
        }).toList();
    }
}
