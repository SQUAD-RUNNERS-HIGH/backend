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

    public PersonalRunningResponse calculatePersonalRunning(final PersonalRunningInfo info){
        Double mySpeed = info.mySpeed();
        double remainTime = info.coursePerimeter() / mySpeed;
        if(remainTime < info.competitorRemainTime()){
            return new PersonalRunningResponse(RunningStatus.LEADING);
        }
        return new PersonalRunningResponse(RunningStatus.LAGGING);
    }

    public void project(ObjectId objectId){
        Query query = new Query(Criteria.where("_id").is(objectId));
        Document course = mongoTemplate.findOne(query, Document.class, "courses");
        GeoJsonReader r = new GeoJsonReader(geometryFactory);
        Geometry geometry;
        try{
            geometry = r.read(course.toJson());
            log.info(geometry.getGeometryType());
            Coordinate[] coordinates = DistanceOp.nearestPoints(geometry,
                geometryFactory.createPoint(new Coordinate(126.7388
                    , 37.54143)));
            log.info(String.valueOf(coordinates[0]));
        }catch (ParseException p){
            log.error(p.getLocalizedMessage());
        }
    }
}
