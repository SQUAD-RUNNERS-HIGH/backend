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
    REQUEST_FIELD_VALIDATION_FAILED(HttpStatus.BAD_REQUEST,"000","요청 파라미터 검증에 실패했습니다"),

    /**
     * Course 관련 에러 코드 100~199
     */
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND,"100","존재하지 않는 코스입니다. 요청파라미터를 확인해주세요.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String msg;
}