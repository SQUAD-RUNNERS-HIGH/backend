package runnershigh.capstone.running.geometry;

import java.util.Map;
import java.util.Objects;
import org.bson.Document;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Component;

@Component
public class GeometryProjectionHandlerMapping {

    private final Map<String, GeometryProjectionHandler> handlerMap;

    public GeometryProjectionHandlerMapping(Map<String, GeometryProjectionHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public GeometryProjectionHandler getHandler(final Document courseDocument) {
        Geometry geometry = GeometryReader.readFrom(courseDocument);
        String key = geometry.getGeometryType().toLowerCase();
        GeometryProjectionHandler handler = handlerMap.get(key);
        if (Objects.isNull(handler)) {
            throw new IllegalArgumentException("Unsupported geometry type: " + key);
        }
        return handler;
    }
}
