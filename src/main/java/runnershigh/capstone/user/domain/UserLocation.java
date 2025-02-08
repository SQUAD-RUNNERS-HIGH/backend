package runnershigh.capstone.user.domain;

import com.google.common.geometry.S2CellId;
import com.google.common.geometry.S2LatLng;
import com.google.common.geometry.S2Point;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import runnershigh.capstone.global.enums.S2Constant;

@Getter
@Embeddable
public class UserLocation {

    private double latitude;
    private double longitude;

    private String cellToken;
    private String cellParentToken;

    public void saveUserLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        S2Point s2Point = S2LatLng.fromDegrees(latitude, longitude).toPoint();
        this.cellToken = S2CellId.fromPoint(s2Point).toToken();
        this.cellParentToken = S2CellId.fromPoint(s2Point)
            .parent(S2Constant.S2_CELL_LEVEL.getValue())
            .toToken();
    }


}
