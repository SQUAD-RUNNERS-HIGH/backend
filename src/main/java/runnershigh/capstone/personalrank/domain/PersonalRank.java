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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseId;
    private String historyId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    private Double runningTime;

    public PersonalRank(final String courseId, final String historyId, final User user,
        final Double runningTime) {
        this.courseId = courseId;
        this.historyId = historyId;
        this.user = user;
        this.runningTime = runningTime;
    }

    public String getUserName(){
        return user.getUsername();
    }
}