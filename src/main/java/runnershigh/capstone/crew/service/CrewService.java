package runnershigh.capstone.crew.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.service.ChatService;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.dto.request.CrewCreateRequest;
import runnershigh.capstone.crew.dto.request.CrewUpdateRequest;
import runnershigh.capstone.crew.dto.response.CrewCreateResponse;
import runnershigh.capstone.crew.dto.response.CrewDeleteResponse;
import runnershigh.capstone.crew.dto.response.CrewUpdateResponse;
import runnershigh.capstone.crew.exception.CrewNotFoundException;
import runnershigh.capstone.crew.repository.CrewRepository;
import runnershigh.capstone.crew.service.mapper.CrewMapper;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.crewscore.service.CrewScoreService;
import runnershigh.capstone.geocoding.dto.FormattedAddressResponse;
import runnershigh.capstone.geocoding.service.GeocodingService;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.location.domain.Location;
import runnershigh.capstone.location.dto.LocationRequest;
import runnershigh.capstone.s3.service.S3Service;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.service.UserService;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CrewService {

    private final CrewMapper crewMapper;

    private final CrewRepository crewRepository;

    private final UserService userService;
    private final GeocodingService geocodingService;
    private final CrewScoreService crewScoreService;
    private final ChatService chatService;
    private final S3Service s3Service;

    private static final String S3_DIRECTORY_NAME = "crew";

    public CrewCreateResponse createCrew(Long crewLeaderId, CrewCreateRequest crewCreateRequest,
        MultipartFile image) {

        User crewLeader = userService.getUser(crewLeaderId);

        FormattedAddressResponse addressResponse = getFormattedAddressResponse(
            crewCreateRequest.crewLocation());

        String imageUrl = s3Service.upload(image, S3_DIRECTORY_NAME);
        Crew crew = crewMapper.toCrew(crewLeader, crewCreateRequest, addressResponse, imageUrl);

        crew.addToCrewAsParticipant(new CrewParticipant(crewLeader));
        crew.createChatRoom(new ChatRoom(crew));

        crewRepository.save(crew);
        crewScoreService.save(crew);

        return new CrewCreateResponse(crew.getId());
    }

    public CrewUpdateResponse updateCrew(Long crewLeaderId, CrewUpdateRequest crewUpdateRequest,
        Long crewId, MultipartFile image) {
        Crew crew = getCrewByIdAndLeaderId(crewId, crewLeaderId);

        FormattedAddressResponse addressResponse = getFormattedAddressResponse(
            crewUpdateRequest.crewLocation());

        Location crewLocation = crewMapper.toCrewLocation(addressResponse,
            crewUpdateRequest.crewLocation().specificLocation());
        String beforeImageUrl = crew.getImage();
        String imageUrl = s3Service.update(beforeImageUrl, image, S3_DIRECTORY_NAME);

        crew.updateCrew(crewUpdateRequest, crewLocation, imageUrl);
        crewRepository.save(crew);
        return new CrewUpdateResponse(crew.getId());
    }

    public CrewDeleteResponse deleteCrew(Long crewLeaderId, Long crewId) {
        Crew crew = getCrewByIdAndLeaderId(crewId, crewLeaderId);
        s3Service.delete(crew.getImage());
        chatService.deleteChatMessages(crew.getChatRoom().getId());
        crewRepository.delete(crew);
        crewScoreService.delete(crew);
        return new CrewDeleteResponse(crew.getId());
    }

    private Crew getCrewByIdAndLeaderId(Long crewId, Long crewLeaderId) {
        return crewRepository.findByIdAndCrewLeaderId(crewId, crewLeaderId)
            .orElseThrow(() -> new CrewNotFoundException(ErrorCode.CREW_NOT_FOUND));
    }

    private FormattedAddressResponse getFormattedAddressResponse(
        LocationRequest locationRequest) {
        return geocodingService.getFormattedAddress(
            locationRequest.latitude(), locationRequest.longitude());
    }
}
