package runnershigh.capstone.crew.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.dto.CrewSearchCondition;

public interface CrewRepositoryCustom {

    Page<Crew> findCrewByCondition(CrewSearchCondition request, Pageable pageable);

    Page<Crew> findNearCrewWithoutParticipation(String city, String dong, Long userId,
        Pageable pageable);
}
