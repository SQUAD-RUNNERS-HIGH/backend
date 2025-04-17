package runnershigh.capstone.running.service;

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
import runnershigh.capstone.running.dto.PersonalRunningInfo;
import runnershigh.capstone.running.dto.PersonalRunningResponse;
import runnershigh.capstone.running.dto.RunningStatus;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalRunningService {

    private static final double ESCAPED_COURSE_STANDARD = 30.0;
    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final MongoTemplate mongoTemplate;

    public PersonalRunningResponse calculatePersonalRunning(
        final PersonalRunningInfo info, final String courseId) {
        log.info("{}", info);
        return projectToCourse(new ObjectId(courseId), new Coordinate(info.longitude(),
            info.latitude()));
    }

    public PersonalRunningResponse projectToCourse(final ObjectId objectId, Coordinate rawUserLocation) {
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
            if (isUserEscapedCourse(projectedUserLocation,rawUserLocation)){
                return new PersonalRunningResponse(RunningStatus.ESCAPED,rawUserLocation.x,
                    rawUserLocation.y);
            }else{
                return new PersonalRunningResponse(RunningStatus.ONGOING,projectedUserLocation.x,
                    projectedUserLocation.y);
            }
        } catch (ParseException p) {
            log.error("GeoJson Parse Error! Course Id : {}, Request Coordinates : {},{}",
                objectId, rawUserLocation.x, rawUserLocation.y);
        }
        return new PersonalRunningResponse(RunningStatus.ERROR,rawUserLocation.x,rawUserLocation.y);
    }

    private boolean isUserEscapedCourse(Coordinate projectedUserLocation,
        Coordinate rawUserLocation) {
        double distance = HaversineCalculator.haversineDistance(projectedUserLocation.x,
            projectedUserLocation.y,
            rawUserLocation.x, rawUserLocation.y);
        return distance >= ESCAPED_COURSE_STANDARD;
    }
}
