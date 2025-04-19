package runnershigh.capstone.running.geometry;

import org.bson.Document;
import runnershigh.capstone.running.domain.UserCoordinate;

public interface GeometryProjectionHandler {

    UserCoordinate project(Document document, UserCoordinate rawUserCoordinate);
}
