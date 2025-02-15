package runnershigh.capstone.crew.exception;

import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.EntityNotFoundException;

public class CrewNotFoundException extends EntityNotFoundException {

    public CrewNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
