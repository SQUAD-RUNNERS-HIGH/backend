package runnershigh.capstone.running.domain;

import org.locationtech.jts.geom.Coordinate;
import runnershigh.capstone.global.util.HarversineCalculator;

public class UserCoordinate extends Coordinate {
    private static final double ESCAPED_COURSE_STANDARD = 30.0;

    public UserCoordinate(final double x, final double y) {
        super(x, y);
    }

    public UserCoordinate(final Coordinate coordinate){
        super(coordinate.x,coordinate.y);
    }

    public double distanceTo(final Coordinate userCoordinate) {
        return HarversineCalculator.calculate(this.x,this.y,userCoordinate.x,userCoordinate.y);
    }

    public boolean isUserEscapedCourse(final Coordinate rawUserCoordinate) {
        double distance = this.distanceTo(rawUserCoordinate);
        return distance >= ESCAPED_COURSE_STANDARD;
    }
}
