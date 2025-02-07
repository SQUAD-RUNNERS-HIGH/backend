package runnershigh.capstone.course.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.course.domain.Course;

@Repository
public interface CourseRepository extends MongoRepository<Course, ObjectId> {
}
