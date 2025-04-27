package runnershigh.capstone.course.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.course.domain.Calorie;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.domain.CourseGeometry;
import runnershigh.capstone.course.domain.CourseProperties;
import runnershigh.capstone.course.domain.Mets;
import runnershigh.capstone.course.domain.Slope;
import runnershigh.capstone.course.dto.request.CourseSaveRequest;
import runnershigh.capstone.course.dto.response.CourseDetailResponse;
import runnershigh.capstone.course.dto.response.CourseListResponse;
import runnershigh.capstone.course.exception.CourseNotFoundException;
import runnershigh.capstone.course.repository.CourseRepository;
import runnershigh.capstone.course.service.mapper.CourseMapper;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.util.HarversineCalculator;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
        final Course course = getCourse(courseObjectId);
        final User user = userService.getUser(userId);
        final Slope slope = slopeService.getSlope(course);
        final Calorie calorie = new Calorie(new Mets(slope), user.getPhysical(), course);
        return courseMapper.toCourseDetailResponse(slope.getElevation(),course,calorie);
    }

    public Course getCourse(final String courseObjectId) {
        return courseRepository.findById(new ObjectId(courseObjectId))
            .orElseThrow(() -> new CourseNotFoundException(ErrorCode.COURSE_NOT_FOUND));
    }

    @Transactional
    public void save(final CourseSaveRequest request, final Long userId){
        final List<List<List<Double>>> coordinates = request.coordinates();
        final List<Double> startPoint = coordinates.get(0).get(0);
        coordinates.get(0).add(startPoint);
        final CourseGeometry polygon = new CourseGeometry("Polygon", coordinates);
        final CourseProperties courseProperties = new CourseProperties(request.courseName(), calculatePerimeter(coordinates));
        final Course course = new Course(courseProperties, polygon);
        courseRepository.save(course);
    }

    public static double calculatePerimeter(List<List<List<Double>>> coordinates) {
        if (coordinates == null || coordinates.isEmpty()) {
            return 0.0;
        }

        List<List<Double>> outerRing = coordinates.get(0); // 외곽선
        if (outerRing.size() < 2) {
            return 0.0;
        }

        double perimeter = 0.0;

        for (int i = 0; i < outerRing.size() - 1; i++) {
            List<Double> pointA = outerRing.get(i);
            List<Double> pointB = outerRing.get(i + 1);
            perimeter +=HarversineCalculator.calculate(pointB.get(0),pointB.get(1),pointA.get(0),pointA.get(1));
        }

        // 마지막 점과 첫 점을 연결
        List<Double> first = outerRing.get(0);
        List<Double> last = outerRing.get(outerRing.size() - 1);
        double dx = last.get(0) - first.get(0);
        double dy = last.get(1) - first.get(1);
        perimeter += HarversineCalculator.calculate(last.get(0),last.get(1),first.get(0),first.get(1));

        return perimeter;
    }
}
