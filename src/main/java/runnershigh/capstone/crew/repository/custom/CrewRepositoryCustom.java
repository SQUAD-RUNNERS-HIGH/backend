package runnershigh.capstone.crew.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import runnershigh.capstone.crew.domain.Crew;

public interface CrewRepositoryCustom {

    Page<Crew> findNearCrewWithoutParticipation(String city, String dong, Long userId,
        Pageable pageable);
}
