package runnershigh.capstone.crew.service;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.dto.CrewSearchCondition;
import runnershigh.capstone.crew.dto.request.CrewCreateRequest;
import runnershigh.capstone.crew.dto.request.CrewSearchRequest;
import runnershigh.capstone.crew.dto.request.CrewUpdateRequest;
import runnershigh.capstone.crew.dto.response.CrewCreateResponse;
import runnershigh.capstone.crew.dto.response.CrewDeleteResponse;
import runnershigh.capstone.crew.dto.response.CrewDetailResponse;
import runnershigh.capstone.crew.dto.response.CrewParticipantsDetailsResponse;
import runnershigh.capstone.crew.dto.response.CrewSearchPagingResponse;
import runnershigh.capstone.crew.dto.response.CrewSimpleResponse;
import runnershigh.capstone.crew.dto.response.CrewUpdateResponse;
import runnershigh.capstone.crew.enums.CrewUserRole;
import runnershigh.capstone.crew.exception.CrewNotFoundException;
import runnershigh.capstone.crew.repository.CrewRepository;
import runnershigh.capstone.crew.service.mapper.CrewMapper;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.crewscore.repository.CrewScoreRepository;
import runnershigh.capstone.crewscore.service.CrewScoreService;
import runnershigh.capstone.gcs.service.GCSService;
import runnershigh.capstone.geocoding.dto.FormattedAddressResponse;
import runnershigh.capstone.geocoding.service.GeocodingService;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.location.domain.Location;
import runnershigh.capstone.location.dto.LocationRequest;
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
    private final CrewScoreService crewScoreService;
    private final GCSService gcsService;

    @Transactional
    public CrewCreateResponse createCrew(Long crewLeaderId, CrewCreateRequest crewCreateRequest) {

        User crewLeader = userService.getUser(crewLeaderId);

        FormattedAddressResponse addressResponse = getFormattedAddressResponse(
            crewCreateRequest.crewLocation());

//        String imageUrl = gcsService.upload(image);
        Crew crew = crewMapper.toCrew(crewLeader, crewCreateRequest, addressResponse);
        crew.addToCrewAsParticipant(new CrewParticipant(crewLeader));

        crewRepository.save(crew);
        crewScoreService.save(crew);

        return new CrewCreateResponse(crew.getId());
    }

    @Transactional(readOnly = true)
    public CrewDetailResponse getCrewDetail(Long userId, Long crewId) {
        Crew crew = getCrewById(crewId);
        crew.saveCrewRank(crewScoreService.getCrewRank(crewId));
        CrewUserRole userRole = crew.validateAndReturnUserRole(userId);
        Double score = crewScoreService.getCrewScore(crewId).getScore();
        return crewMapper.toCrewDetailResponse(crew, userRole,score);
    }

    @Transactional(readOnly = true)
    public Set<CrewParticipantsDetailsResponse> getCrewParticipants(Long crewId) {
        Crew crew = getCrewByIdWithParticipants(crewId);
        return crewMapper.toCrewParticipantsDetailsResponse(crew.getCrewParticipant());
    }

    @Transactional
    public CrewUpdateResponse updateCrew(Long crewLeaderId, CrewUpdateRequest crewUpdateRequest,
        MultipartFile image) {
        Crew crew = getCrewByLeaderId(crewLeaderId);

        FormattedAddressResponse addressResponse = getFormattedAddressResponse(
            crewUpdateRequest.crewLocation());

        Location crewLocation = crewMapper.toCrewLocation(addressResponse,
            crewUpdateRequest.crewLocation().specificLocation());
        String beforeImageUrl = crew.getImage();
        String imageUrl = gcsService.update(beforeImageUrl, image);

        crew.updateCrew(crewUpdateRequest, crewLocation, imageUrl);
        crewRepository.save(crew);
        return new CrewUpdateResponse(crew.getId());
    }

    @Transactional
    public CrewDeleteResponse deleteCrew(Long crewLeaderId) {
        Crew crew = getCrewByLeaderId(crewLeaderId);
        gcsService.delete(crew.getImage());
        crewRepository.delete(crew);
        return new CrewDeleteResponse(crew.getId());
    }

    @Transactional
    public CrewSearchPagingResponse<CrewSimpleResponse> searchCrew(
        CrewSearchRequest crewSearchRequest,
        Pageable pageable) {

        CrewSearchCondition crewSearchCondition = crewMapper.toCrewSearchCondition(
            crewSearchRequest);

        Page<Crew> crews = crewRepository.findCrewByCondition(crewSearchCondition,
            pageable);
        Page<CrewSimpleResponse> crewSearchResponse = crewMapper.toCrewSimplePagingResponse(crews);

        return CrewSearchPagingResponse.from(crewSearchResponse);
    }

    @Transactional(readOnly = true)
    public CrewSearchPagingResponse<CrewSimpleResponse> getCrewNearby(Long userId,
        Pageable pageable) {

        User user = userService.getUser(userId);
        String city = user.getUserLocation().getCity();
        String dong = user.getUserLocation().getDong();

        Page<Crew> crews = crewRepository.findByCrewLocation_CityAndCrewLocation_Dong(city, dong,
            pageable);

        Page<CrewSimpleResponse> crewNearbyResponses = crewMapper.toCrewSimplePagingResponse(crews);

        return CrewSearchPagingResponse.from(crewNearbyResponses);
    }

    public Crew getCrewById(Long crewId) {
        return crewRepository.findById(crewId)
            .orElseThrow(() -> new CrewNotFoundException(ErrorCode.CREW_NOT_FOUND));
    }

    private Crew getCrewByLeaderId(Long crewLeaderId) {
        return crewRepository.findByCrewLeaderId(crewLeaderId)
            .orElseThrow(() -> new CrewNotFoundException(ErrorCode.CREW_NOT_FOUND));
    }

    private Crew getCrewByIdWithParticipants(Long crewId) {
        return crewRepository.findByIdWithParticipants(crewId)
            .orElseThrow(() -> new CrewNotFoundException(ErrorCode.CREW_NOT_FOUND));
    }

    private FormattedAddressResponse getFormattedAddressResponse(
        LocationRequest locationRequest) {

        return geocodingService.getFormattedAddress(
            locationRequest.latitude(), locationRequest.longitude());
    }


}
