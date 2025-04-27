package runnershigh.capstone.course.dto.request;

import java.util.List;

public record CourseSaveRequest(
    List<List<List<Double>>> coordinates,
    String courseName
) {

}
