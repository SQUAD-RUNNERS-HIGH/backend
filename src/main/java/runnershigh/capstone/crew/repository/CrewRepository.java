package runnershigh.capstone.crew.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.repository.custom.CrewRepositoryCustom;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Long>, CrewRepositoryCustom {

    Optional<Crew> findByCrewLeaderId(Long crewLeaderId);

    Page<Crew> findByCrewLocation_CityAndCrewLocation_Dong(String city, String dong,
        Pageable pageable);
}
