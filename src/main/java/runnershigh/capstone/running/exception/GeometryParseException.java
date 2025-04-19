package runnershigh.capstone.running.exception;

import org.locationtech.jts.geom.Geometry;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.global.exception.GeometryException;

public class GeometryParseException extends GeometryException {

    public GeometryParseException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
