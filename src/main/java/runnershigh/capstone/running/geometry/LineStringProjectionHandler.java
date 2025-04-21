package runnershigh.capstone.running.geometry;

import org.bson.Document;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.linearref.LinearLocation;
import org.locationtech.jts.linearref.LocationIndexedLine;
import org.springframework.stereotype.Component;
import runnershigh.capstone.running.domain.UserCoordinate;

@Component("linestring")
public class LineStringProjectionHandler implements GeometryProjectionHandler{

    @Override
    public UserCoordinate project(final Document document, final Coordinate rawUserCoordinate) {
        LineString lineString = (LineString) GeometryReader.readFrom(document);
        LocationIndexedLine indexedLine = new LocationIndexedLine(lineString);
        LinearLocation project = indexedLine.project(rawUserCoordinate);
        return new UserCoordinate(indexedLine.extractPoint(project));
    }
}
