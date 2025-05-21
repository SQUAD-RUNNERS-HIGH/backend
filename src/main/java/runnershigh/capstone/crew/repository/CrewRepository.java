package runnershigh.capstone.crew.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.repository.custom.CrewRepositoryCustom;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Long>, CrewRepositoryCustom {

    Optional<Crew> findByIdAndCrewLeaderId(Long id, Long crewLeaderId);

    @Query("select c from Crew c "
        + "join fetch c.crewParticipant cp "
        + "join fetch cp.participant "
        + "where c.id = :crewId")
    Optional<Crew> findByIdWithParticipants(@Param("crewId") Long crewId);

    @Query(value = """
        SELECT * 
        FROM crew 
        WHERE MATCH(specific_location) 
              AGAINST(:keyword IN BOOLEAN MODE)
        """, nativeQuery = true)
    Page<Crew> searchBySpecificLocation(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = """
        SELECT * 
        FROM crew 
        WHERE MATCH(name) 
              AGAINST(:keyword IN BOOLEAN MODE)
        """, nativeQuery = true)
    Page<Crew> searchByName(@Param("keyword") String keyword, Pageable pageable);
}
