package runnershigh.capstone.running.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.locationtech.jts.operation.distance.DistanceOp;
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

    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final MongoTemplate mongoTemplate;

    public PersonalRunningResponse calculatePersonalRunning(
        final PersonalRunningInfo info, final String courseId) {
        log.info("{}",info);
        Coordinate coordinate = projectToCourse(new ObjectId(courseId), info.longitude(),
            info.latitude());
        return new PersonalRunningResponse(RunningStatus.ONGOING,coordinate.x,coordinate.y);
    }

    public Coordinate projectToCourse(final ObjectId objectId, final double longitude,
        final double latitude) {
        Document course = mongoTemplate.findOne(new Query(Criteria.where("_id").is(objectId)),
            Document.class, "courses");
        GeoJsonReader r = new GeoJsonReader(geometryFactory);
        Geometry geometry;
        try {
            geometry = r.read(course.toJson());
            Coordinate[] coordinates = DistanceOp.nearestPoints(geometry,
                geometryFactory.createPoint(new Coordinate(longitude
                    , latitude)));
            return coordinates[0];
        } catch (ParseException p) {
            log.error("GeoJson Parse Error! Course Id : {}, Request Coordinates : {},{}",
                objectId, latitude, longitude);
        }
        return new Coordinate(longitude, latitude);
    }
}
