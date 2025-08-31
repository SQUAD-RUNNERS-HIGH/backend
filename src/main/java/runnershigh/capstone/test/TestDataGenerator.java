package runnershigh.capstone.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.repository.CourseRepository;
import runnershigh.capstone.course.service.CourseService;
import runnershigh.capstone.location.domain.Location;
import runnershigh.capstone.personalrunninghistory.dto.PersonalRunningHistoryRequest;
import runnershigh.capstone.personalrunninghistory.service.PersonalRunningHistoryService;
import runnershigh.capstone.user.domain.Goal;
import runnershigh.capstone.user.domain.Physical;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.repository.UserRepository;

@RestController
@RequiredArgsConstructor
public class TestDataGenerator {

    private final UserRepository userRepository;
    private final PersonalRunningHistoryService personalRunningHistoryService;
    private final CourseRepository courseRepository;
    private final CourseService courseService;

    @PostMapping("/api/test-data/{courseId}")
    public void saveTestData(@PathVariable String courseId) {
        User user = userRepository.save(
            new User("test", "test", "t", "러너스하이", 37.3456, 126.7375, new Physical(), new Goal(),
                new Location()));
        Course course = courseService.getCourse(courseId);
        double perimeter = course.getProperties().getPerimeter();
        int size = course.getGeometry().getCoordinates().get(0).size();
        double runningTime = (perimeter / 7000.0) * 3600;
        List<Double> list = new ArrayList<>();
        list.add(0.0);
        Random rand = new Random();
        for (int i = 1; i < size - 1; i++) {
            list.add(rand.nextDouble() * 100);
        }
        list.add(100.0);
        Collections.sort(list);
        PersonalRunningHistoryRequest personalRunningHistoryRequest =
            new PersonalRunningHistoryRequest(
                list, runningTime, courseId);
        personalRunningHistoryService.savePersonalRunningHistory(personalRunningHistoryRequest,
            user.getId());
    }

}
