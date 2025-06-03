package runnershigh.capstone.crew.service;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.dto.request.CrewSearchRequest;
import runnershigh.capstone.crew.dto.response.CrewDetailResponse;
import runnershigh.capstone.crew.dto.response.CrewParticipantsDetailsResponse;
import runnershigh.capstone.crew.dto.response.CrewSearchPagingResponse;
import runnershigh.capstone.crew.dto.response.CrewSimpleResponse;
import runnershigh.capstone.crew.enums.CrewUserRole;
import runnershigh.capstone.crew.exception.CrewNotFoundException;
import runnershigh.capstone.crew.repository.CrewRepository;
import runnershigh.capstone.crew.service.mapper.CrewMapper;
import runnershigh.capstone.crewparticipant.service.CrewParticipantService;
import runnershigh.capstone.crewscore.service.CrewScoreService;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewQueryService {

    private final CrewMapper crewMapper;

    private final CrewRepository crewRepository;

    private final CrewParticipantService crewParticipantService;
    private final CrewScoreService crewScoreService;
    private final UserService userService;

    public CrewDetailResponse getCrewDetail(Long userId, Long crewId) {
        Crew crew = getCrewById(crewId);
        crew.saveCrewRank(crewScoreService.getCrewRank(crewId));
        CrewUserRole userRole = crew.validateAndReturnUserRole(userId);
        Double score = crewScoreService.getCrewScore(crewId).getScore();
        return crewMapper.toCrewDetailResponse(crew, userRole, score);
    }

    public Set<CrewParticipantsDetailsResponse> getCrewParticipants(Long crewId) {
        Crew crew = getCrewByIdWithParticipants(crewId);
        return crewMapper.toCrewParticipantsDetailsResponse(crew.getCrewParticipant());
    }

    public CrewSearchPagingResponse<CrewSimpleResponse> searchCrew(
        CrewSearchRequest crewSearchRequest,
        Pageable pageable) {

        if (crewSearchRequest.name() != null) {
            Page<Crew> crews = crewRepository.searchByName(crewSearchRequest.name(), pageable);
            Page<CrewSimpleResponse> crewSimpleResponses = crewMapper.toCrewSimplePagingResponse(
                crews);
            return CrewSearchPagingResponse.from(crewSimpleResponses);

        } else if (crewSearchRequest.region() != null) {
            Page<Crew> crews = crewRepository.searchBySpecificLocation(crewSearchRequest.region(),
                pageable);
            Page<CrewSimpleResponse> crewSimpleResponses = crewMapper.toCrewSimplePagingResponse(
                crews);
            return CrewSearchPagingResponse.from(crewSimpleResponses);
        }

        return null;
    }

    public CrewSearchPagingResponse<CrewSimpleResponse> getCrewNearby(Long userId,
        Pageable pageable) {

        User user = userService.getUser(userId);
        Page<Crew> crews = getCrews(userId, pageable, user);
        Page<CrewSimpleResponse> crewNearbyResponses = crewMapper.toCrewSimplePagingResponse(crews);

        return CrewSearchPagingResponse.from(crewNearbyResponses);
    }

    public List<Crew> getCrewsByUserId(Long userId) {
        return crewParticipantService.getCrewsByUserId(userId);
    }

    public Crew getCrewById(Long crewId) {
        return crewRepository.findById(crewId)
            .orElseThrow(() -> new CrewNotFoundException(ErrorCode.CREW_NOT_FOUND));
    }

    private Page<Crew> getCrews(Long userId, Pageable pageable, User user) {
        String city = user.getUserLocation().getCity();
        String dong = user.getUserLocation().getDong();

        return crewRepository.findNearCrewWithoutParticipation(city, dong, userId,
            pageable);
    }

    private Crew getCrewByIdWithParticipants(Long crewId) {
        return crewRepository.findByIdWithParticipants(crewId)
            .orElseThrow(() -> new CrewNotFoundException(ErrorCode.CREW_NOT_FOUND));
    }


}
