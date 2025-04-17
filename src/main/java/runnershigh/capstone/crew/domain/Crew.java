package runnershigh.capstone.crew.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import runnershigh.capstone.crew.dto.CrewUpdateRequest;
import runnershigh.capstone.crew.exception.CrewNotFoundException;
import runnershigh.capstone.crewapplication.exception.CrewApplicationNotFoundException;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.user.domain.User;

@Entity
@Getter
@NoArgsConstructor
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    private int maxCapacity;
    private int userCount;
    private String image;

    @Embedded
    private CrewLocation crewLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User crewLeader;

    @OneToMany(mappedBy = "crew", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CrewParticipant> crewParticipant;

    @Builder
    public Crew(String name, String description, int maxCapacity, String image,
        CrewLocation crewLocation, User crewLeader, Set<CrewParticipant> crewParticipant) {
        this.name = name;
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.userCount = crewParticipant.size();
        this.image = image;
        this.crewLocation = crewLocation;
        this.crewLeader = crewLeader;
        this.crewParticipant = crewParticipant;
    }

    public void updateCrew(CrewUpdateRequest crewUpdateRequest, CrewLocation crewLocation) {
        this.name = crewUpdateRequest.name();
        this.description = crewUpdateRequest.description();
        this.maxCapacity = crewUpdateRequest.maxCapacity();
        this.image = crewUpdateRequest.image();
        this.crewLocation = crewLocation;
    }

    public void addToCrewAsParticipant(CrewParticipant crewParticipant) {
        this.crewParticipant.add(crewParticipant);
        crewParticipant.addCrew(this);
        this.userCount = this.crewParticipant.size();
    }

    public void validationCrewLeader(Long crewLeaderId) {
        if (!crewLeader.getId().equals(crewLeaderId)) {
            throw new CrewNotFoundException(ErrorCode.CREW_LEADER_VALIDATION_FAILED);
        }
    }

    public void validationCrewParticipant(Long applicantId) {
        this.crewParticipant.stream().filter(c -> c.getParticipant().getId().equals(applicantId))
            .findFirst().ifPresent(c -> {
                throw new CrewApplicationNotFoundException(ErrorCode.EXISTED_CREW_PARTICIPANT);
            });
    }

    public void validationCrewAvailableCapacity() {
        if (this.userCount == this.maxCapacity) {
            throw new CrewApplicationNotFoundException(ErrorCode.FULL_CREW_PARTICIPANT);
        }
    }
}
