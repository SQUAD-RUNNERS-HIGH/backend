package runnershigh.capstone.builders;

import runnershigh.capstone.location.domain.Location;
import runnershigh.capstone.user.domain.Goal;
import runnershigh.capstone.user.domain.Physical;
import runnershigh.capstone.user.domain.User;

public class UserTestBuilder {

    private String loginId = "defaultLoginId";
    private String password = "defaultPassword";
    private String username = "defaultUsername";
    private Physical physical = new Physical();
    private Goal goal = new Goal();
    private Location location = new Location();
    private double latitude = 37.5665;   // 기본 위도 (서울 좌표 예시)
    private double longitude = 126.9780; // 기본 경도 (서울 좌표 예시)

    public static UserTestBuilder builder() {
        return new UserTestBuilder();
    }

    public UserTestBuilder loginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public UserTestBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserTestBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserTestBuilder physical(Physical physical) {
        this.physical = physical;
        return this;
    }

    public UserTestBuilder goal(Goal goal) {
        this.goal = goal;
        return this;
    }

    public UserTestBuilder location(Location location) {
        this.location = location;
        return this;
    }

    public UserTestBuilder latitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public UserTestBuilder longitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public User build() {
        String passwordSalt = "defaultPasswordSalt";
        return new User(loginId, password, passwordSalt, username,
            latitude, longitude, physical, goal, location);
    }

}
