package runnershigh.capstone.personalrunninghistory.exception;

import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.EntityNotFoundException;

public class HistoryNotFoundException extends EntityNotFoundException {

    public HistoryNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
