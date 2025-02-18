package runnershigh.capstone.course.service.mapper;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import runnershigh.capstone.course.domain.CourseElevation;
import runnershigh.capstone.course.domain.Elevation;
import runnershigh.capstone.course.infrastructure.ElevationResponse;
import runnershigh.capstone.course.infrastructure.ElevationResponse.LocationResponse;

@Component
public class ElevationMapper {

    public Elevation toElevation(final ElevationResponse response, final ObjectId courseId) {
        return Elevation.builder()
            .courseElevations(toCourseElevations(response.results()))
            .courseId(courseId)
            .build();
    }

    private List<CourseElevation> toCourseElevations(List<LocationResponse> locationResponses) {
        return locationResponses.stream()
            .map(l -> new CourseElevation(l.elevation())).toList();
    }

}
