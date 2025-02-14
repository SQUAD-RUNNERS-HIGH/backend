package runnershigh.capstone.course.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.dto.CourseListResponse;
import runnershigh.capstone.course.dto.CourseResponse;
import runnershigh.capstone.course.exception.CourseNotFoundException;
import runnershigh.capstone.course.repository.CourseRepository;
import runnershigh.capstone.course.service.mapper.CourseMapper;
import runnershigh.capstone.elevation.dto.ElevationResponse;
import runnershigh.capstone.elevation.service.ElevationService;
import runnershigh.capstone.global.error.ErrorCode;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ElevationService elevationService;
    private final CourseMapper courseMapper;

    public CourseListResponse getCourse(final double longitude, final double latitude) {
        final List<Course> courses = courseRepository.findByCurrentCoordinates(longitude,
            latitude, 3 / 3963.2);
        return courseMapper.toCourseLocationResponse(courses);
    }

    public ElevationResponse getCourseDetail(final String courseObjectId) {
        Course course =
            courseRepository.findById(new ObjectId(courseObjectId))
                .orElseThrow(() -> new CourseNotFoundException(
                    ErrorCode.COURSE_NOT_FOUND
                ));
        return elevationService.getElevations(course);
    }
}
