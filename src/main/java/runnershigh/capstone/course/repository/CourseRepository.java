package runnershigh.capstone.course.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import runnershigh.capstone.course.domain.Course;

public interface CourseRepository extends MongoRepository<Course, ObjectId> {
}
