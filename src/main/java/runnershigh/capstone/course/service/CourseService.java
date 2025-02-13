package runnershigh.capstone.course.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.repository.CourseRepository;
import runnershigh.capstone.course.service.mapper.CourseMapper;
import runnershigh.capstone.elevation.dto.ElevationResponse;
import runnershigh.capstone.elevation.service.ElevationService;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ElevationService elevationService;
    private final CourseMapper courseMapper;

    public List<Course> getCourse(final double longitude, final double latitude){
        return  courseRepository.findByCurrentCoordinates(longitude,
            latitude, 3 / 3963.2);
    }

    public ElevationResponse getCourseDetail(final String courseObjectId){
        Course course = courseRepository.findById(new ObjectId(courseObjectId)).get();
        return elevationService.getElevations(course);
    }
}
