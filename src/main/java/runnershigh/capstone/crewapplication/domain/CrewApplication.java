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
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.user.domain.User;

@Entity
@Getter
@NoArgsConstructor
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

    private LocalDate applicationDate;

    @Builder
    public CrewApplication(Crew crew, User applicant) {
        this.crew = crew;
        this.applicant = applicant;
        this.status = CrewApplicationStatus.PENDING;
        this.applicationDate = LocalDate.now();
    }

    public void approve(Long crewLeaderId) {
        crew.validationCrewAvailableCapacity();
        crew.validationCrewLeader(crewLeaderId);
        this.status = CrewApplicationStatus.APPROVAL;
        crew.addToCrewAsParticipant(new CrewParticipant(applicant));
    }

    public void refuse(Long crewLeaderId) {
        crew.validationCrewLeader(crewLeaderId);
        this.status = CrewApplicationStatus.REFUSAL;
    }
}
