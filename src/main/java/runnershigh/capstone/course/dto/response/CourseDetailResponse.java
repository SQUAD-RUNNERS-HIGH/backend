package runnershigh.capstone.course.dto.response;

import java.util.List;
import lombok.Builder;
import runnershigh.capstone.course.domain.CourseElevation;
import runnershigh.capstone.course.infrastructure.ElevationResponse;

@Builder
public record CourseDetailResponse(
    double minCalorie,
    double maxCalorie,
    double perimeter,
    List<CourseElevation> courseElevations,
    String courseName
) {

}
