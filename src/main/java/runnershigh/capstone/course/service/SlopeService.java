package runnershigh.capstone.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.domain.Slope;
import runnershigh.capstone.course.infrastructure.ElevationClient;
import runnershigh.capstone.course.infrastructure.ElevationRequestParameterGenerator;
import runnershigh.capstone.course.infrastructure.ElevationRequest;
import runnershigh.capstone.course.infrastructure.ElevationResponse;

@Service
@RequiredArgsConstructor
public class SlopeService {

    private final ElevationClient elevationClient;

    public Slope getSlope(final Course course){
        ElevationRequest locations = ElevationRequestParameterGenerator.generate(
            course);
        ElevationResponse response = elevationClient.getElevation(locations);
        return new Slope(response,course);
    }
}
