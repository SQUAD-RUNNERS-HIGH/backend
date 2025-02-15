package runnershigh.capstone.user.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import runnershigh.capstone.user.dto.UserProfileRequest;


@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String loginId;
    private String password;
    private String passwordSalt;
    private String username;

    @Embedded
    private Physical physical;

    @Embedded
    private Goal goal;

    @Embedded
    private UserLocation userLocation;

    @Builder
    public User(String loginId, String password, String passwordSalt, String username,
        Physical physical, Goal goal, UserLocation userLocation) {
        this.loginId = loginId;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.username = username;
        this.physical = physical;
        this.goal = goal;
        this.userLocation = userLocation;
    }

    public void updateProfile(UserProfileRequest userProfileRequest) {
        this.password = userProfileRequest.password();
        this.username = userProfileRequest.username();
        this.physical = userProfileRequest.physical();
    }
}
