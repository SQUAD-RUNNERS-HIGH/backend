package runnershigh.capstone.crew.service;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.dto.CrewCreateRequest;
import runnershigh.capstone.crew.dto.CrewCreateResponse;
import runnershigh.capstone.crew.dto.CrewDeleteResponse;
import runnershigh.capstone.crew.dto.CrewDetailResponse;
import runnershigh.capstone.crew.dto.CrewParticipantsDetailsResponse;
import runnershigh.capstone.crew.dto.CrewUpdateRequest;
import runnershigh.capstone.crew.dto.CrewUpdateResponse;
import runnershigh.capstone.crew.exception.CrewNotFoundException;
import runnershigh.capstone.crew.repository.CrewRepository;
import runnershigh.capstone.crew.service.mapper.CrewMapper;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.geocoding.dto.FormattedAddressResponse;
import runnershigh.capstone.geocoding.service.GeocodingService;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.service.UserService;

@Service
@AllArgsConstructor
@Slf4j
public class CrewService {

    private final CrewRepository crewRepository;
    private final CrewMapper crewMapper;
    private final UserService userService;
    private final GeocodingService geocodingService;

    @Transactional
    public CrewCreateResponse createCrew(Long crewLeaderId, CrewCreateRequest crewCreateRequest) {

        User crewLeader = userService.getUser(crewLeaderId);

        FormattedAddressResponse addressResponse = getFormattedAddressResponse(crewCreateRequest);

        Crew crew = crewMapper.toCrew(crewLeader, crewCreateRequest, addressResponse);
        crew.addToCrewAsParticipant(new CrewParticipant(crewLeader));

        crewRepository.save(crew);

        return new CrewCreateResponse(crew.getId());
    }

    @Transactional(readOnly = true)
    public CrewDetailResponse getCrewDetail(Long crewId) {
        Crew crew = getCrewById(crewId);
        return crewMapper.toCrewDetailResponse(crew);
    }

    @Transactional(readOnly = true)
    public Set<CrewParticipantsDetailsResponse> getCrewParticipants(Long crewId) {
        Crew crew = getCrewById(crewId);
        return crewMapper.toCrewParticipantsDetailsResponse(crew.getCrewParticipant());
    }

    @Transactional
    public CrewUpdateResponse updateCrew(Long crewLeaderId, CrewUpdateRequest crewUpdateRequest) {
        Crew crew = getCrewByLeaderId(crewLeaderId);

        crew.updateCrew(crewUpdateRequest);
        crewRepository.save(crew);
        return new CrewUpdateResponse(crew.getId());
    }

    @Transactional
    public CrewDeleteResponse deleteCrew(Long crewLeaderId) {
        Crew crew = getCrewByLeaderId(crewLeaderId);

        crewRepository.delete(crew);
        return new CrewDeleteResponse(crew.getId());
    }

    private Crew getCrewByLeaderId(Long crewLeaderId) {
        return crewRepository.findByCrewLeaderId(crewLeaderId)
            .orElseThrow(() -> new CrewNotFoundException(ErrorCode.CREW_NOT_FOUND));
    }

    public Crew getCrewById(Long crewId) {
        return crewRepository.findById(crewId)
            .orElseThrow(() -> new CrewNotFoundException(ErrorCode.CREW_NOT_FOUND));
    }

    private FormattedAddressResponse getFormattedAddressResponse(
        CrewCreateRequest crewCreateRequest) {

        return geocodingService.getFormattedAddress(
            crewCreateRequest.latitude(), crewCreateRequest.longitude());
    }


}
