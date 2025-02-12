package runnershigh.capstone.course.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.dto.CourseCoordinate;
import runnershigh.capstone.course.dto.CourseLocationResponse;
import runnershigh.capstone.course.repository.CourseRepository;
import runnershigh.capstone.course.service.mapper.CourseMapper;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public List<Course> getCourse(final double longitude, final double latitude){
        return  courseRepository.findByCurrentCoordinates(longitude,
            latitude, 3 / 3963.2);
    }
}
