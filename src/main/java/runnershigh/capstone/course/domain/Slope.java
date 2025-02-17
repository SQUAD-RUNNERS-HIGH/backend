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
    private final ElevationResponse response;

    public Slope(final ElevationResponse response, final Course course) {
        this.response = response;
        this.grade = calculate(response.results(),course.getProperties().getPerimeter());
    }

    private double calculate(final List<LocationResponse> results, final double perimeter){
        double totalAscent = IntStream.range(1, results.size())
            .mapToDouble(i -> {
                double diff = results.get(i).elevation() - results.get(i - 1).elevation();
                return diff > NONE_DIFFERENCE ? diff : NONE_DIFFERENCE;
            })
            .sum();
        return (totalAscent/perimeter)*100;
    }
}
