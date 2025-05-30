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

    OPTIMISTIC_LOCK_CONFLICT(HttpStatus.CONFLICT, "001", "요청이 충돌하여 처리되지 않았습니다. 다시 시도해주세요."),

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
     * CrewApplication 관련 에러 코드 300~349
     */
    CREW_APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "300", "존재하지 않는 크루 지원입니다. 요청파라미터를 확인해주세요."),

    CREW_APPLICATION_DUPLICATION(HttpStatus.BAD_REQUEST, "301", "해당 크루에 이미 지원했습니다."),

    EXISTED_CREW_PARTICIPANT(HttpStatus.BAD_REQUEST, "302", "해당 크루에 이미 참가하여 있습니다."),

    FULL_CREW_PARTICIPANT(HttpStatus.BAD_REQUEST, "303", "해당 크루는 수용인원이 모두 채워져있습니다."),

    /**
     * CrewParticipant 관련 에러 코드 350~399
     */
    CREW_PARTICIPANT_NOT_FOUND(HttpStatus.NOT_FOUND, "350", "존재하지 않는 크루 참가자입니다."),

    /**
     * 인증 관련 에러 코드 400~449
     */
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "400", "비밀번호가 일치하지 않습니다."),

    INVALID_TOKEN(HttpStatus.NOT_FOUND, "401", "토큰이 유효하지 않습니다."),

    /**
     * Chat 관련 에러 코드 450~499
     */
    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "450", "존재하지 않는 채팅방입니다."),

    /**
     * User 관련 에러 코드 500~549
     */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "500", "존재하지 않는 유저입니다."),

    LOGIN_ID_DUPLICATION(HttpStatus.BAD_REQUEST, "501", "이미 존재하는 ID 입니다."),

    USERNAME_DUPLICATION(HttpStatus.BAD_REQUEST, "502", "이미 존재하는 이름입니다."),

    /**
     * UserLocation 관련 에러 코드 550 ~ 599
     */
    USER_LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "550", "존재하지 않는 유저 위치입니다."),

    IS_SAME_USER_LOCATION(HttpStatus.BAD_REQUEST, "551", "메인 위치는 삭제할 수 없습니다."),

    /**
     * Personal Running History 관련 에러 코드 600~699
     */
    HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "600", "러닝기록을 찾을 수 없습니다"),

    /**
     * Geocoding 관련 에러 코드 700~749
     */
    GEOCODING_NOT_FOUND(HttpStatus.NOT_FOUND, "700", "존재하지 않는 주소입니다."),

    INCORRECT_GEOCODING_FORMAT(HttpStatus.BAD_REQUEST, "701", "요청된 GeoCoding 포맷과 다릅니다."),

    GEOCODING_EXTRACT_FAILED(HttpStatus.BAD_REQUEST, "702", "GEOCODING 주소 추출을 실패했습니다."),

    /**
     * File 관련 에러 코드 750~799
     */
    FILE_CONVERT_FAILED(HttpStatus.NOT_FOUND, "750", "Convert 실패"),

    FILE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "751", "파일 업로드 실패"),

    FILE_DELETE_FAILED(HttpStatus.BAD_REQUEST, "752", "파일 삭제 실패"),

    /**
     * Geometry 관련 에러 코드 800~899
     */
    COULD_NOT_PARSE_GEOMETRY(HttpStatus.BAD_REQUEST, "800",
        "GeoJson데이터를 적절한 Geometry로 변환할 수 없습니다."),

    /**
     * Crew Score 관련 에러 코드 900~999
     */
    CREW_SCORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "900", "크루 점수 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String msg;
}