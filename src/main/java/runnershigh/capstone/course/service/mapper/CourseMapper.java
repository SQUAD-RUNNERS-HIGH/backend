package runnershigh.capstone.course.service.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.dto.CourseCoordinate;
import runnershigh.capstone.course.dto.CourseLocationResponse;

@Component
public class CourseMapper {

    public CourseLocationResponse from(final List<Course> courses){
        List<List<List<CourseCoordinate>>> response = courses.stream()
            .map(c -> toCourseCoordinateList(c.getGeometry().getCoordinates(),c.getId()))
            .toList();
        return new CourseLocationResponse(response);
    }

    private List<List<CourseCoordinate>> toCourseCoordinateList(List<List<List<Double>>> coordinates,
        ObjectId id){
        return coordinates.stream()
            .map(outerList -> outerList.stream()
                .map(innerList -> {
                    return new CourseCoordinate(innerList.get(0),innerList.get(1));
                })
                .toList())
            .toList();
    }
}
