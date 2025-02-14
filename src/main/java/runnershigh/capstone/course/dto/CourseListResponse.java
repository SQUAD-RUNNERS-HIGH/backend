package runnershigh.capstone.course.dto;

import java.util.List;

public record CourseListResponse(
    List<CourseResponse> courseResponses
) {

}
