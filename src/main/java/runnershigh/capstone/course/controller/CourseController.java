package runnershigh.capstone.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.course.dto.CourseDetailResponse;
import runnershigh.capstone.course.dto.CourseListResponse;
import runnershigh.capstone.course.service.CourseService;
import runnershigh.capstone.global.argumentresolver.AuthUser;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.response.ApiErrorCodeExamples;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "코스 [주변 코스 조회]")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @Operation(summary = "주변 코스 조회", description = "중심 좌표 기준 3km 근방 코스를 반환합니다.")
    public CourseListResponse getCourse(@RequestParam final double longitude,
        @RequestParam final double latitude) {
        return courseService.getNearByCourses(longitude, latitude);
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "코스 단건 상세 조회", description = "코스 이름, 고도 정보, 예상 칼로리(최소,최대 2가지 반환), 거리(KM "
        + "단위)를 반환합니다.")
    @ApiErrorCodeExamples({ErrorCode.COURSE_NOT_FOUND})
    public CourseDetailResponse getCourseDetail(@Parameter(hidden = true) @AuthUser Long userId,
        @PathVariable final String courseId) {
        return courseService.getCourseDetail(courseId, userId);
    }
}
