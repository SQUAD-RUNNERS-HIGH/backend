package runnershigh.capstone.geocoding.exception;

import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.EntityNotFoundException;

public class GeocodingNotFoundException extends EntityNotFoundException {

    public GeocodingNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
