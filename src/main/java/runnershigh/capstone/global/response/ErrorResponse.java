package runnershigh.capstone.global.response;

import runnershigh.capstone.global.error.ErrorCode;

public record ErrorResponse(
        int httpStatusCode, String httpStatusMessage, String serverErrorCode,
        String serverErrorMessage) {

    public ErrorResponse(final ErrorCode errorCode) {
        this(
                errorCode.getHttpStatus().value(),
                errorCode.getHttpStatus().getReasonPhrase(),
                errorCode.getCode(),
                errorCode.getMsg());
    }

    public ErrorResponse(final ErrorCode errorCode, final String detailMsg){
        this(
            errorCode.getHttpStatus().value(),
            errorCode.getHttpStatus().getReasonPhrase(),
            errorCode.getCode(),
            detailMsg);
    }
}
