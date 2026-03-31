package runnershigh.capstone.course.infrastructure;

import java.util.List;
import java.util.stream.Collectors;
import runnershigh.capstone.course.domain.Course;
import runnershigh.capstone.course.consts.GeoJsonType;

public class ElevationRequestParameterGenerator {

    public static String generate(final Course course) {
        if (course.getGeometry().getType().equals(GeoJsonType.LINE_STRING.getType())) {
            return fromLineString(course.getGeometry().getCoordinates());
        } else {
            return fromPolygon(course.getGeometry().getCoordinates());
        }
    }

    public static String fromLineString(final List<List<List<Double>>> coordinates) {
        return coordinates.stream()
            .map(outer -> outer.get(1).get(0) + "," + outer.get(0).get(0))
            .collect(Collectors.joining("|"));
    }

    public static String fromPolygon(final List<List<List<Double>>> coordinates) {
        return coordinates.stream()
            .flatMap(outer -> outer.stream())
            .map(inner -> inner.get(1) + "," + inner.get(0))
            .collect(Collectors.joining("|"));
    }
}
