package runnershigh.capstone.running.geometry;

import org.bson.Document;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.linearref.LinearLocation;
import org.locationtech.jts.linearref.LocationIndexedLine;
import org.springframework.stereotype.Component;
import runnershigh.capstone.running.domain.UserCoordinate;

@Component("polygon")
public class PolygonProjectionHandler implements GeometryProjectionHandler{

    @Override
    public UserCoordinate project(final Document document, final UserCoordinate rawUserCoordinate) {
        Polygon polygon = (Polygon) GeometryReader.readFrom(document);
        LinearRing exteriorRing = polygon.getExteriorRing();
        LocationIndexedLine indexedLine = new LocationIndexedLine(exteriorRing);
        LinearLocation project = indexedLine.project(rawUserCoordinate);
        return (UserCoordinate)indexedLine.extractPoint(project);
    }
}
