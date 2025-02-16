package runnershigh.capstone.crewparticipant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;

@Repository
public interface CrewParticipantRepository extends JpaRepository<CrewParticipant, Long> {

}
