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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    private int maxCapacity;
    private String image;

    @Embedded
    private CrewLocation crewLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User crewLeader;

    @OneToMany(mappedBy = "crew", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CrewParticipant> crewParticipant;

    public void addToCrewAsParticipant(CrewParticipant crewParticipant) {
        this.crewParticipant.add(crewParticipant);
        crewParticipant.addCrew(this);
    }

    public void validationCrewLeader(Long crewLeaderId) {
        if (!crewLeader.getId().equals(crewLeaderId)) {
            throw new IllegalArgumentException("해당 크루의 크루 리더 외엔 접근이 불가능합니다.");
        }
    }
}
