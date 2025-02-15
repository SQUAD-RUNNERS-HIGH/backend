package runnershigh.capstone.user.exception;

import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
