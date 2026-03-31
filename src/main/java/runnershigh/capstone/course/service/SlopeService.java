package runnershigh.capstone.course.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.domain.Elevation;
import runnershigh.capstone.course.domain.Slope;
import runnershigh.capstone.course.infrastructure.ElevationClient;
import runnershigh.capstone.course.infrastructure.ElevationRequestParameterGenerator;
import runnershigh.capstone.course.repository.ElevationRepository;
import runnershigh.capstone.course.service.mapper.ElevationMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlopeService {

    private final ElevationClient elevationClient;
    private final ElevationRepository elevationRepository;
    private final ElevationMapper elevationMapper;

    public Slope getSlope(final Course course){
        String locations = ElevationRequestParameterGenerator.generate(course);
        log.info("[Elevation] locations 파라미터: {}", locations);
        Elevation elevation = elevationRepository.findByCourseId(course.getId())
            .orElseGet(() -> getElevationFromElevationClient(course, locations));
        return new Slope(elevation,course);
    }

    private Elevation getElevationFromElevationClient(final Course course, final String locations) {
        Elevation elevation = elevationMapper.toElevation(elevationClient.getElevation(locations),
            course.getId());
        elevationRepository.save(elevation);
        return elevation;
    }
}
