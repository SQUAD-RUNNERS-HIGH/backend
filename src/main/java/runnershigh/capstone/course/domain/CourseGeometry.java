package runnershigh.capstone.course.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseGeometry {
    private String type;
    private List<List<List<Double>>> coordinates;
}
