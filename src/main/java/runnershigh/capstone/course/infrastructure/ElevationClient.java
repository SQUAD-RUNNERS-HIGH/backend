package runnershigh.capstone.course.infrastructure;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface ElevationClient {

    @PostExchange
    ElevationResponse getElevation(@RequestBody ElevationRequest locations);
}
