package runnershigh.capstone.course.exception;

import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.EntityNotFoundException;

public class CourseNotFoundException extends EntityNotFoundException {

    public CourseNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
