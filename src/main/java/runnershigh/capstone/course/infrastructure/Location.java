package runnershigh.capstone.course.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Location {
    @JsonProperty("lat")
    private final double latitude;
    @JsonProperty("lng")
    private final double longitude;
}
