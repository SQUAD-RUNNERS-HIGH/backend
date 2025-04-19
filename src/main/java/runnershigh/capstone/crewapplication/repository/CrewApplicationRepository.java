package runnershigh.capstone.crewapplication.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.crewapplication.domain.CrewApplication;

@Repository
public interface CrewApplicationRepository extends JpaRepository<CrewApplication, Long> {

    Optional<CrewApplication> findByApplicantIdAndCrewId(Long userId, Long crewId);

    List<CrewApplication> findCrewApplicationsByCrewId(Long crewId);

    Boolean existsByApplicantIdAndCrewId(Long applicantId, Long crewId);
}
