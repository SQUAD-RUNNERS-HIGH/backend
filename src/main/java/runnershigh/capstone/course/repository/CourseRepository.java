package runnershigh.capstone.course.repository;


import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import runnershigh.capstone.course.domain.Course;

public interface CourseRepository extends MongoRepository<Course, ObjectId> {

    @Query("{ 'geometry': { $geoWithin: { $centerSphere:  [[?0,?1 ], ?2] } }}")
    List<Course> findByCurrentCoordinates(double longitude,double latitude,double radius);
}
