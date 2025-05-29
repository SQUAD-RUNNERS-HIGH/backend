package runnershigh.capstone.chat.exception;

import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.EntityNotFoundException;

public class ChatNotFoundException extends EntityNotFoundException {

    public ChatNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
