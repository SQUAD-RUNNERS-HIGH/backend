package runnershigh.capstone.course.service.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import runnershigh.capstone.course.domain.Calorie;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.dto.CourseDetailResponse;
import runnershigh.capstone.course.dto.CourseListResponse;
import runnershigh.capstone.course.dto.CourseResponse;
import runnershigh.capstone.course.infrastructure.ElevationResponse;

@Component
public class CourseMapper {

    public CourseListResponse toCourseLocationResponse(final List<Course> courses){
        List<CourseResponse> courseResponses = courses.stream()
            .map(c -> new CourseResponse(c.getGeometry().getCoordinates(),
                c.getId().toString())).toList();
        return new CourseListResponse(courseResponses);
    }

    public CourseDetailResponse toCourseDetailResponse(final ElevationResponse elevationResponse,
        final Course course,final Calorie calorie){
        return CourseDetailResponse.builder()
            .elevationResponse(elevationResponse)
            .courseName(course.getProperties().getName())
            .perimeter(course.getProperties().getPerimeter())
            .maxCalorie(calorie.getMaxCalorie())
            .minCalorie(calorie.getMinCalorie())
            .build();
    }
}
