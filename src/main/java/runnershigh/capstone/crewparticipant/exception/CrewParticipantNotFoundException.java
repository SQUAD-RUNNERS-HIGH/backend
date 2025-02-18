package runnershigh.capstone.crewparticipant.exception;

import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.EntityNotFoundException;

public class CrewParticipantNotFoundException extends EntityNotFoundException {

    public CrewParticipantNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
