package runnershigh.capstone.course.repository;

import jakarta.persistence.Id;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import runnershigh.capstone.course.domain.Elevation;


public interface ElevationRepository extends MongoRepository<Elevation,ObjectId> {

    Optional<Elevation> findByCourseId(ObjectId objectId);
}
