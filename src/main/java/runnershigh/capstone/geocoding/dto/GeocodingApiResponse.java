package runnershigh.capstone.geocoding.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeocodingApiResponse(
    @JsonProperty("results") List<Result> results,
    @JsonProperty("status") String status
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Result(
        @JsonProperty("formatted_address") String formattedAddress
    ) {

    }
}
