package runnershigh.capstone.course.repository;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourseDocumentRepository {

    private final MongoTemplate mongoTemplate;

    public Document findByObjectId(final ObjectId objectId){
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(objectId)),
            Document.class, "courses");
    }
}
