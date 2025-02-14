package runnershigh.capstone.elevation;

import lombok.Getter;

@Getter
public enum GeoJsonType {
    POLYGON("Polygon"),
    LINE_STRING("LineString");

    private final String type;

    GeoJsonType(final String type) {
        this.type = type;
    }
}
