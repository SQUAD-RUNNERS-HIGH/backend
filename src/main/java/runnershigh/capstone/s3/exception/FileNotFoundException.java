package runnershigh.capstone.s3.exception;

import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.FileException;

public class FileNotFoundException extends FileException {

    public FileNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }

}
