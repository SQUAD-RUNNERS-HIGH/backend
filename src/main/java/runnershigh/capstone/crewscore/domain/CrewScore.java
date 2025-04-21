package runnershigh.capstone.crewscore.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import runnershigh.capstone.crew.domain.Crew;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrewScore {

    private static final Double INITIAL_SCORE = 0.;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;

    @OneToOne
    private Crew crew;

    public CrewScore(final Crew crew) {
        this.score = INITIAL_SCORE;
        this.crew = crew;
    }

    public void updateScore(final int numberOfRunner, final double perimeter){
        this.score += (double)numberOfRunner * perimeter;
    }
}
