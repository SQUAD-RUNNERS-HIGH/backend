package runnershigh.capstone.course.dto;

import java.util.List;

public record CourseLocationResponse(
    List<List<List<CourseCoordinate>>> coordinates
) {

}
