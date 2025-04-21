package runnershigh.capstone.running.geometry;

import org.bson.Document;
import org.locationtech.jts.geom.Coordinate;
import runnershigh.capstone.running.domain.UserCoordinate;

public interface GeometryProjectionHandler {

    UserCoordinate project(Document document, Coordinate rawUserCoordinate);
}
