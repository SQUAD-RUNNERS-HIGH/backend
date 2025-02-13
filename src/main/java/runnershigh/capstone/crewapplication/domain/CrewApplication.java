package runnershigh.capstone.crewapplication.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrewApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Crew crew;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User applicant;

    @Enumerated(EnumType.STRING)
    private CrewApplicationStatus status;

    public void approve(Long crewLeaderId) {
        crew.validationCrewLeader(crewLeaderId);
        this.status = CrewApplicationStatus.APPROVAL;
        crew.addToCrewAsParticipant(new CrewParticipant(applicant));
    }

    public void refuse(Long crewLeaderId) {
        crew.validationCrewLeader(crewLeaderId);
        this.status = CrewApplicationStatus.REFUSAL;
    }
}
