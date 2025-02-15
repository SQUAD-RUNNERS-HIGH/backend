package runnershigh.capstone.crewapplication.exception;

import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.EntityNotFoundException;

public class CrewApplicationNotFoundException extends EntityNotFoundException {

    public CrewApplicationNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
