package runnershigh.capstone.crewparticipant.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;

@Repository
public interface CrewParticipantRepository extends JpaRepository<CrewParticipant, Long> {

    Optional<CrewParticipant> findByParticipantIdAndCrewId(Long participantId, Long crewId);

    @Query("select cp from CrewParticipant cp join fetch cp.crew where cp.participant.id = :userId")
    List<CrewParticipant> findByUserId(Long userId);
}
