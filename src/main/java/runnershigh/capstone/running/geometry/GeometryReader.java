package runnershigh.capstone.running.geometry;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.running.exception.GeometryParseException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeometryReader {

    public static Geometry readFrom(final Document courseDocument){
        Geometry geometry = null;
        try{
            Document geometryJson = (Document)courseDocument.get("geometry");
            geometry = new GeoJsonReader(new GeometryFactory()).read(geometryJson.toJson());
        }catch (ParseException e){
            log.error(e.getLocalizedMessage());
            throw new GeometryParseException(ErrorCode.COULD_NOT_PARSE_GEOMETRY);
        }
        return geometry;
    }
}
