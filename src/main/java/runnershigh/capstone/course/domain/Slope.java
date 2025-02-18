package runnershigh.capstone.course.domain;

import java.util.List;
import java.util.stream.IntStream;
import lombok.Getter;
import runnershigh.capstone.course.infrastructure.ElevationResponse;
import runnershigh.capstone.course.infrastructure.ElevationResponse.LocationResponse;

@Getter
public class Slope {

    private final double grade;
    private static final Integer NONE_DIFFERENCE = 0;
    private final Elevation elevation;

    public Slope(final Elevation elevation, final Course course) {
        this.elevation = elevation;
        this.grade = calculate(elevation.getCourseElevations(),course.getProperties().getPerimeter());
    }

    private double calculate(final List<CourseElevation> results, final double perimeter){
        double totalAscent = IntStream.range(1, results.size())
            .mapToDouble(i -> {
                double diff = results.get(i).elevation() - results.get(i - 1).elevation();
                return diff > NONE_DIFFERENCE ? diff : NONE_DIFFERENCE;
            })
            .sum();
        return (totalAscent/perimeter)/100;
    }
}
