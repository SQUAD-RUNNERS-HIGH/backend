package runnershigh.capstone.crewapplication.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.crewapplication.domain.CrewApplication;

@Repository
public interface CrewApplicationRepository extends JpaRepository<CrewApplication, Long> {

    Optional<CrewApplication> findByApplicantIdAndCrewId(Long applicantId, Long crewId);

    Boolean existsByApplicantIdAndCrewId(Long applicantId, Long crewId);
}
