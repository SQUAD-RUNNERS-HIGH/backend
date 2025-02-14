package runnershigh.capstone.elevation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.elevation.ElevationRequestParameterGenerator;
import runnershigh.capstone.elevation.dto.ElevationRequest;
import runnershigh.capstone.elevation.infrastructure.ElevationClient;
import runnershigh.capstone.elevation.dto.ElevationResponse;

@Service
@RequiredArgsConstructor
public class ElevationService {

    private final ElevationClient elevationClient;

    public ElevationResponse getElevations(final Course course){
        ElevationRequest locations = ElevationRequestParameterGenerator.generate(
            course);
        return elevationClient.getElevation(locations);
    }
}
