package runnershigh.capstone.course.infrastructure;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface ElevationClient {

    @PostExchange("https://api.open-elevation.com/api/v1/lookup")
    ElevationResponse getElevation(@RequestBody ElevationRequest locations);
}
