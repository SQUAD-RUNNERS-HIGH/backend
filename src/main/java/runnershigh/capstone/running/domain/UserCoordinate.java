package runnershigh.capstone.running.domain;

import org.locationtech.jts.geom.Coordinate;

public class UserCoordinate extends Coordinate {

    private static final double EARTH_RADIUS = 6371000;
    private static final double ESCAPED_COURSE_STANDARD = 30.0;

    public UserCoordinate(final double x, final double y) {
        super(x, y);
    }

    public UserCoordinate(final Coordinate coordinate){
        super(coordinate.x,coordinate.y);
    }

    private double haversineDistance(double lon1, double lat1, double lon2, double lat2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
            + Math.cos(lat1Rad) * Math.cos(lat2Rad)
            * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public double distanceTo(final Coordinate userCoordinate) {
        return haversineDistance(this.x,this.y,userCoordinate.x,userCoordinate.y);
    }

    public boolean isUserEscapedCourse(final Coordinate rawUserCoordinate) {
        double distance = this.distanceTo(rawUserCoordinate);
        return distance >= ESCAPED_COURSE_STANDARD;
    }
}
