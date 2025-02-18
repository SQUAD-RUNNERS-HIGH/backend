package runnershigh.capstone.personalrank.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import runnershigh.capstone.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalRank {

    private static final Integer TO_MINUTES = 6000;
    private static final Integer TO_SECONDS = 1000;
    private static final Integer TO_MILLISECONDS = 1000;
    private static final String RUNNING_TIME_FORMAT = "%02d:%02d.%03d";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    private Long runningTime;

    public String toRunningTimeStringFormat(){
        long minutes = (runningTime / TO_MINUTES) % 60;
        long seconds = (runningTime / TO_SECONDS) % 60;
        long milliseconds = runningTime % TO_MILLISECONDS;
        return String.format(RUNNING_TIME_FORMAT, minutes, seconds, milliseconds);
    }

    public String getUserName(){
        return user.getUsername();
    }
}