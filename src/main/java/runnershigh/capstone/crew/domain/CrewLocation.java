package runnershigh.capstone.crew.domain;


import com.google.common.geometry.S2CellId;
import com.google.common.geometry.S2LatLng;
import com.google.common.geometry.S2Point;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import runnershigh.capstone.global.enums.S2Constant;

@Getter
@Embeddable
@NoArgsConstructor
public class CrewLocation {

    private static final Logger log = LoggerFactory.getLogger(CrewLocation.class);
    private double latitude;
    private double longitude;

    private String cellToken;
    private String cellParentToken;


    public CrewLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        S2Point s2Point = S2LatLng.fromDegrees(latitude, longitude).toPoint();
        this.cellToken = S2CellId.fromPoint(s2Point).toToken();
        this.cellParentToken = S2CellId.fromPoint(s2Point)
            .parent(S2Constant.S2_CELL_LEVEL.getValue())
            .toToken();
    }


}
