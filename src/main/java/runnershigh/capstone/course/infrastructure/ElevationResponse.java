package runnershigh.capstone.course.infrastructure;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ElevationResponse(
    List<LocationResponse> results
) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record LocationResponse(
        double latitude,
        double longitude,
        double elevation
    ) {
    }
}
