package runnershigh.capstone.crewparticipant.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;

@Repository
public interface CrewParticipantRepository extends JpaRepository<CrewParticipant, Long> {

    Optional<CrewParticipant> findByParticipantIdAndCrewId(Long participantId, Long crewId);
}
