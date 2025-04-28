package runnershigh.capstone.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import runnershigh.capstone.global.error.ErrorCode;

@Getter
@RequiredArgsConstructor
public class FileException extends RuntimeException {

    private final ErrorCode errorCode;

}
