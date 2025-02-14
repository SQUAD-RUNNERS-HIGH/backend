package runnershigh.capstone.elevation;

import java.util.ArrayList;
import java.util.List;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.elevation.dto.ElevationRequest;
import runnershigh.capstone.elevation.dto.Location;

public class ElevationRequestParameterGenerator {

    public static ElevationRequest generate(final Course course) {
        if (course.getGeometry().getType().equals(GeoJsonType.LINE_STRING.getType())) {
            return fromLineString(course.getGeometry().getCoordinates());
        } else {
            return fromPolygon(course.getGeometry().getCoordinates());
        }
    }

    public static ElevationRequest fromLineString(
        final List<List<List<Double>>> coordinates) {
        ArrayList<Location> locations = new ArrayList<>();
        coordinates.forEach(
            outer -> locations.add(new Location(outer.get(1).get(0), outer.get(0).get(0))));
        return new ElevationRequest(locations);
    }

    public static ElevationRequest fromPolygon(
        final List<List<List<Double>>> coordinates) {
        ArrayList<Location> locations = new ArrayList<>();
        coordinates.forEach(
            outer -> outer.forEach(inner -> locations.add(new Location(
                inner.get(1), inner.get(0)))));
        return new ElevationRequest(locations);
    }

}
