package runnershigh.capstone.running.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.locationtech.jts.linearref.LinearLocation;
import org.locationtech.jts.linearref.LocationIndexedLine;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import runnershigh.capstone.running.dto.CrewParticipantInfoRequest;
import runnershigh.capstone.running.dto.CrewParticipantInfoResponse;
import runnershigh.capstone.running.dto.CrewRunningInfoRequest;
import runnershigh.capstone.running.dto.PersonalRunningInfoRequest;
import runnershigh.capstone.running.dto.PersonalRunningResponse;
import runnershigh.capstone.running.dto.RunningStatus;
import runnershigh.capstone.running.repository.CrewRunningRedisRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RunningService {

    private static final double ESCAPED_COURSE_STANDARD = 30.0;
    private static final int MINIMUM_CREW_RUNNING_PARTICIPANTS = 3;
    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final MongoTemplate mongoTemplate;
    private final CrewRunningRedisRepository crewRunningRedisRepository;

    public PersonalRunningResponse calculatePersonalRunning(
        final PersonalRunningInfoRequest info, final String courseId) {
        log.info("{}", info);
        return projectToCourse(new ObjectId(courseId), new Coordinate(info.longitude(),
            info.latitude()));
    }

    public void calculateCrewRunning(final CrewRunningInfoRequest request,
        final String courseId, final String crewId){

    }

    public CrewParticipantInfoResponse sendLocation(
        final CrewParticipantInfoRequest request,
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
            userIds.size() == readyStatus.size() && isAllReady(readyStatus,
            userIds)) {
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

    public PersonalRunningResponse projectToCourse(final ObjectId objectId,
        Coordinate rawUserLocation) {
        Document course = mongoTemplate.findOne(new Query(Criteria.where("_id").is(objectId)),
            Document.class, "courses");
        GeoJsonReader r = new GeoJsonReader(geometryFactory);
        Polygon polygon;

        try {
            polygon = (Polygon) r.read(course.toJson());
            LinearRing exteriorRing = polygon.getExteriorRing();
            LocationIndexedLine indexedLine = new LocationIndexedLine(exteriorRing);
            LinearLocation project = indexedLine.project(rawUserLocation);
            Coordinate projectedUserLocation = indexedLine.extractPoint(project);
            if (isUserEscapedCourse(projectedUserLocation, rawUserLocation)) {
                return new PersonalRunningResponse(RunningStatus.ESCAPED, rawUserLocation.x,
                    rawUserLocation.y);
            } else {
                return new PersonalRunningResponse(RunningStatus.ONGOING, projectedUserLocation.x,
                    projectedUserLocation.y);
            }
        } catch (ParseException p) {
            log.error("GeoJson Parse Error! Course Id : {}, Request Coordinates : {},{}",
                objectId, rawUserLocation.x, rawUserLocation.y);
        }
        return new PersonalRunningResponse(RunningStatus.ERROR, rawUserLocation.x,
            rawUserLocation.y);
    }

    private boolean isUserEscapedCourse(Coordinate projectedUserLocation,
        Coordinate rawUserLocation) {
        double distance = HaversineCalculator.haversineDistance(projectedUserLocation.x,
            projectedUserLocation.y,
            rawUserLocation.x, rawUserLocation.y);
        return distance >= ESCAPED_COURSE_STANDARD;
    }
}
