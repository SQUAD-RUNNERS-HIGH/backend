package runnershigh.capstone.course.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.domain.Calorie;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.domain.Mets;
import runnershigh.capstone.course.domain.Slope;
import runnershigh.capstone.course.dto.CourseDetailResponse;
import runnershigh.capstone.course.dto.CourseListResponse;
import runnershigh.capstone.course.exception.CourseNotFoundException;
import runnershigh.capstone.course.repository.CourseRepository;
import runnershigh.capstone.course.service.mapper.CourseMapper;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.service.UserService;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserService userService;
    private final SlopeService slopeService;

    public CourseListResponse getNearByCourses(final double longitude, final double latitude) {
        final List<Course> courses = courseRepository.findByCurrentCoordinates(longitude,
            latitude, 3 / 3963.2);
        return courseMapper.toCourseLocationResponse(courses);
    }

    public CourseDetailResponse getCourseDetail(final String courseObjectId, final Long userId) {
        Course course = getCourse(courseObjectId);
        User user = userService.getUser(userId);
        Slope slope = slopeService.getSlope(course);
        Calorie calorie = new Calorie(new Mets(slope), user.getPhysical(), course);
        return courseMapper.toCourseDetailResponse(slope.getResponse(),course,calorie);
    }

    public Course getCourse(final String courseObjectId) {
        return courseRepository.findById(new ObjectId(courseObjectId))
            .orElseThrow(() -> new CourseNotFoundException(ErrorCode.COURSE_NOT_FOUND));
    }
}
