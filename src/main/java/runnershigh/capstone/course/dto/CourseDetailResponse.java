package runnershigh.capstone.course.dto;

import lombok.Builder;
import runnershigh.capstone.course.infrastructure.ElevationResponse;

@Builder
public record CourseDetailResponse(
    double minCalorie,
    double maxCalorie,
    double perimeter,
    ElevationResponse elevationResponse,
    String courseName
) {

}
