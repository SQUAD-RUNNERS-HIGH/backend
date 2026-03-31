package runnershigh.capstone.course.infrastructure;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface ElevationClient {

    @GetExchange
    ElevationResponse getElevation(@RequestParam("locations") String locations);
}
