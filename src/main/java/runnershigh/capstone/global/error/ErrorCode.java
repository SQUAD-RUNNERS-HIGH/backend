package runnershigh.capstone.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    /**
     * Global
     */
    REQUEST_FIELD_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "000", "요청 파라미터 검증에 실패했습니다"),

    /**
     * Course 관련 에러 코드 100~199
     */
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "100", "존재하지 않는 코스입니다. 요청파라미터를 확인해주세요."),

    /**
     * Crew 관련 에러 코드 200~299
     */
    CREW_NOT_FOUND(HttpStatus.NOT_FOUND, "200", "존재하지 않는 크루입니다. 올바른 크루를 요청하였는지 확인해주세요"),

    CREW_LEADER_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "201", "올바른 크루 리더가 아닙니다."),

    /**
     * CrewApplication 관련 에러 코드 300~399
     */
    CREW_APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "300", "존재하지 않는 크루 지원입니다. 요청파라미터를 확인해주세요."),

    CREW_APPLICATION_DUPLICATED(HttpStatus.BAD_REQUEST, "301", "해당 크루에 이미 지원했습니다."),

    EXISTED_CREW_PARTICIPANT(HttpStatus.BAD_REQUEST, "302", "해당 크루에 이미 참가하여 있습니다."),

    FULL_CREW_PARTICIPANT(HttpStatus.BAD_REQUEST, "303", "해당 크루는 수용인원이 모두 채워져있습니다."),
    /**
     * User 관련 에러 코드 400~499
     */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "400", "존재하지 않는 유저입니다.");
    
    private final HttpStatus httpStatus;
    private final String code;
    private final String msg;
}