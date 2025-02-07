package runnershigh.capstone.course.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.dto.CourseResponse;
import runnershigh.capstone.course.repository.CourseRepository;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course getCourse(final String courseId){
        return courseRepository.findById(new ObjectId(courseId)).get();
    }
}
