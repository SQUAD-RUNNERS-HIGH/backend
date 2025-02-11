package runnershigh.capstone.crew.service;

import com.google.common.geometry.S1Angle;
import com.google.common.geometry.S2CellId;
import com.google.common.geometry.S2LatLng;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.domain.CrewLocation;
import runnershigh.capstone.crew.dto.CrewCreateRequest;
import runnershigh.capstone.crew.dto.CrewCreateResponse;
import runnershigh.capstone.crew.dto.CrewSearchResponse;
import runnershigh.capstone.crew.repository.CrewRepository;
import runnershigh.capstone.crew.service.mapper.CrewMapper;
import runnershigh.capstone.user.domain.UserLocation;
import runnershigh.capstone.user.service.UserService;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CrewService {

    private final CrewRepository crewRepository;
    private final CrewMapper crewMapper;
    private final UserService userService;

    private static final double EARTH_RADIUS = 6378.137;
    private static final double MAX_SEARCH_KM = 10.0;

    public CrewCreateResponse createCrew(Long crewLeaderId, CrewCreateRequest crewCreateRequest) {

        Crew crew = crewMapper.toCrew(userService.getUser(crewLeaderId), crewCreateRequest);

        crewRepository.save(crew);

        return new CrewCreateResponse(crew.getId());
    }

    public CrewSearchResponse searchCrew(Long userId) {
        UserLocation userLocation = userService.getUser(userId).getUserLocation();

        List<Crew> surroundCrews = crewRepository.findByCellParentToken(
            userLocation.getCellParentToken());
        log.info("surroundCrews: {}", surroundCrews);

        return new CrewSearchResponse(surroundCrews.stream()
            .filter(crew -> calculateDistance(crew.getCrewLocation(), userLocation.getCellToken())
                <= MAX_SEARCH_KM)
            .collect(Collectors.toList()));
    }

    private double calculateDistance(CrewLocation crewLocation, String userCellToken) {
        S2LatLng crewS2LatLng = S2CellId.fromToken(crewLocation.getCellToken()).toLatLng();
        S2LatLng userS2LatLng = S2CellId.fromToken(userCellToken.toLowerCase()).toLatLng();

        log.info("Crew Location: {}, User Location: {}", crewS2LatLng, userS2LatLng);
        S1Angle angle = crewS2LatLng.getDistance(userS2LatLng);

        double distanceKM = angle.radians() * EARTH_RADIUS;
        log.info("{} km", distanceKM);
        return distanceKM;
    }


}
