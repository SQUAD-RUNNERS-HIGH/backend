package runnershigh.capstone.course.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.dto.CourseLocationResponse;
import runnershigh.capstone.course.service.CourseService;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<Course> getCourse(@RequestParam final double longitude,
        @RequestParam final double latitude){
        return courseService.getCourse(longitude,latitude);
    }
}
