package runnershigh.capstone.crewscore.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.crewscore.domain.CrewScore;

@Repository
public interface CrewScoreRepository extends JpaRepository<CrewScore, Long> {

    @Query("select cr from CrewScore cr where cr.crew.id = :crewId")
    Optional<CrewScore> findByCrewId(Long crewId);
}
