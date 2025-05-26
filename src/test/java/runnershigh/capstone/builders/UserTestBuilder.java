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

    public User build() {
        String passwordSalt = "defaultPasswordSalt";
        return new User(loginId, password, passwordSalt, username, physical, goal, location);
    }
}
