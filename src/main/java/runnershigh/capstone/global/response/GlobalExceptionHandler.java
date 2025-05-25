package runnershigh.capstone.global.response;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.EntityNotFoundException;
import runnershigh.capstone.global.exception.FileException;
import runnershigh.capstone.global.exception.InvalidCredentialsException;

@RestControllerAdvice
@Slf4j
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEntityNotFound(final EntityNotFoundException e) {
        loggingError(e.getErrorCode());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidCredentials(final InvalidCredentialsException e) {
        loggingError(e.getErrorCode());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFileException(final FileException e) {
        loggingError(e.getErrorCode());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler({
        ObjectOptimisticLockingFailureException.class,
        OptimisticLockException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleOptimisticLockException(Exception e) {
        log.error("Optimistic Lock Conflict: {}", e.getMessage());
        return new ErrorResponse(ErrorCode.OPTIMISTIC_LOCK_CONFLICT);
    }


    private void loggingError(final ErrorCode errorCode) {
        log.error("ErrorCode : {} , Message : {}", errorCode.getCode(), errorCode.getMsg());
    }
}
