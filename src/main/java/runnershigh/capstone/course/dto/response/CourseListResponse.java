package runnershigh.capstone.course.dto.response;

import java.util.List;

public record CourseListResponse(
    List<CourseResponse> courseResponses
) {

}
