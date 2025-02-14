package runnershigh.capstone.course.dto;

import runnershigh.capstone.course.infrastructure.ElevationResponse;

public record CourseDetailResponse(
    double expectedCalories,
    double perimeter,
    ElevationResponse elevationResponse,
    String courseName
) {

}
