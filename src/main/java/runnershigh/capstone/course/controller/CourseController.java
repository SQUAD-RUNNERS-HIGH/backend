package runnershigh.capstone.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.dto.CourseResponse;
import runnershigh.capstone.course.service.CourseService;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable final String courseId){
        return courseService.getCourse(courseId);
    }
}
