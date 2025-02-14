package runnershigh.capstone.course.dto;

import java.util.List;

public record CourseResponse(
    List<List<List<Double>>> coordinates,
    String courseId
) {

}
