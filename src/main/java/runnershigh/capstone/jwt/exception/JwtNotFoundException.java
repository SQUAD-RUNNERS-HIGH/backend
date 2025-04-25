package runnershigh.capstone.jwt.exception;

import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.EntityNotFoundException;

public class JwtNotFoundException extends EntityNotFoundException {

    public JwtNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
