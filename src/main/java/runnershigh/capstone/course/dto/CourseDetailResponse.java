package runnershigh.capstone.course.dto;

import lombok.Builder;
import runnershigh.capstone.course.infrastructure.ElevationResponse;
import runnershigh.capstone.course.infrastructure.ElevationResponse.LocationResponse;

@Builder
public record CourseDetailResponse(
    double minCalorie,
    double maxCalorie,
    double perimeter,
     elevationResponse,
    String courseName
) {

}
