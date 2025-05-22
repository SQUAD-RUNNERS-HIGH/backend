package runnershigh.capstone.crewparticipant.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.user.domain.User;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrewParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Crew crew;

    @Version
    private Long version;

    @Builder
    public CrewParticipant(User participant, Crew crew) {
        this.participant = participant;
        this.crew = crew;
    }

    public CrewParticipant(User applicant) {
        this.participant = applicant;
    }

    public void addCrew(Crew crew) {
        this.crew = crew;
    }


}
