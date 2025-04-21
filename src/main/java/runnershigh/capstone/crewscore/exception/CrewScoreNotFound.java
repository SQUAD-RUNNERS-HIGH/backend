package runnershigh.capstone.crewscore.exception;

import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.EntityNotFoundException;

public class CrewScoreNotFound extends EntityNotFoundException {

    public CrewScoreNotFound(final ErrorCode errorCode) {
        super(errorCode);
    }
}
