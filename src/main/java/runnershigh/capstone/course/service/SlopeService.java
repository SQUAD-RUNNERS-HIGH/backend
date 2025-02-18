package runnershigh.capstone.course.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.domain.Elevation;
import runnershigh.capstone.course.domain.Slope;
import runnershigh.capstone.course.infrastructure.ElevationClient;
import runnershigh.capstone.course.infrastructure.ElevationRequestParameterGenerator;
import runnershigh.capstone.course.infrastructure.ElevationRequest;
import runnershigh.capstone.course.infrastructure.ElevationResponse;
import runnershigh.capstone.course.repository.ElevationRepository;
import runnershigh.capstone.course.service.mapper.ElevationMapper;

@Service
@RequiredArgsConstructor
public class SlopeService {

    private final ElevationClient elevationClient;
    private final ElevationRepository elevationRepository;
    private final ElevationMapper elevationMapper;

    public Slope getSlope(final Course course){
        ElevationRequest locations = ElevationRequestParameterGenerator.generate(
            course);
        Elevation elevation = elevationRepository.findByCourseId(course.getId())
            .orElseGet(() -> getElevationFromElevationClient(course, locations));
        return new Slope(elevation,course);
    }

    private Elevation getElevationFromElevationClient(final Course course, final ElevationRequest locations) {
        Elevation elevation = elevationMapper.toElevation(elevationClient.getElevation(locations),
            course.getId());
        elevationRepository.save(elevation);
        return elevation;
    }
}
