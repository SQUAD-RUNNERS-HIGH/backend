package runnershigh.capstone.crew.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.crew.domain.Crew;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {

    @Query("SELECT c FROM Crew c WHERE c.crewLocation.cellParentToken LIKE :cellParentToken%")
    List<Crew> findByCellParentToken(@Param("cellParentToken") String cellParentToken);
}
