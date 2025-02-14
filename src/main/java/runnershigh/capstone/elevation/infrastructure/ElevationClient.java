package runnershigh.capstone.elevation.infrastructure;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import runnershigh.capstone.elevation.dto.ElevationRequest;
import runnershigh.capstone.elevation.dto.ElevationResponse;

public interface ElevationClient {

    @PostExchange("https://api.open-elevation.com/api/v1/lookup")
    ElevationResponse getElevation(@RequestBody ElevationRequest locations);
}
