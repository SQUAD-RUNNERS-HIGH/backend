package runnershigh.capstone.geocoding.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeocodingApiResponse(
    @JsonProperty("response") Response response
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Response(
        @JsonProperty("status") String status,
        @JsonProperty("result") List<Result> result
    ) {

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Result(
        @JsonProperty("text") String text,
        @JsonProperty("structure") Structure structure
    ) {

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Structure(
        @JsonProperty("level0") String level0,
        @JsonProperty("level1") String level1,
        @JsonProperty("level2") String level2,
        @JsonProperty("level4L") String level4L

    ) {

    }

    public FormattedAddressResponse toFormattedAddressResponse() {
        Result result = response.result().get(0);
        Structure structure = result.structure();

        return new FormattedAddressResponse(
            structure.level0(),
            structure.level1(),
            structure.level2(),
            structure.level4L()
        );
    }
}
