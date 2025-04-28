package runnershigh.capstone.crew.service.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.dto.CrewSearchCondition;
import runnershigh.capstone.crew.dto.request.CrewCreateRequest;
import runnershigh.capstone.crew.dto.request.CrewSearchRequest;
import runnershigh.capstone.crew.dto.response.CrewDetailResponse;
import runnershigh.capstone.crew.dto.response.CrewParticipantsDetailsResponse;
import runnershigh.capstone.crew.dto.response.CrewSimpleResponse;
import runnershigh.capstone.crew.enums.CrewUserRole;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.geocoding.dto.FormattedAddressResponse;
import runnershigh.capstone.location.domain.Location;
import runnershigh.capstone.location.dto.LocationResponse;
import runnershigh.capstone.user.domain.User;

@Component
public class CrewMapper {

    public Crew toCrew(User crewLeader, CrewCreateRequest crewCreateRequest,
        FormattedAddressResponse formattedAddressResponse, String imageUrl) {
        return Crew.builder()
            .name(crewCreateRequest.name())
            .description(crewCreateRequest.description())
            .maxCapacity(crewCreateRequest.maxCapacity())
            .image(imageUrl)
            .crewLocation(toCrewLocation(formattedAddressResponse, crewCreateRequest.crewLocation()
                .specificLocation()))
            .crewLeader(crewLeader)
            .crewParticipant(new HashSet<>())
            .build();
    }

    public Location toCrewLocation(FormattedAddressResponse formattedAddressResponse,
        String specificLocation) {
        return Location.builder()
            .country(formattedAddressResponse.country())
            .city(formattedAddressResponse.city())
            .province(formattedAddressResponse.province())
            .dong(formattedAddressResponse.dong())
            .specificLocation(specificLocation)
            .build();
    }

    public CrewDetailResponse toCrewDetailResponse(Crew crew, CrewUserRole crewUserRole) {
        return CrewDetailResponse.builder()
            .name(crew.getName())
            .description(crew.getDescription())
            .maxCapacity(crew.getMaxCapacity())
            .userCount(crew.getUserCount())
            .image(crew.getImage())
            .crewLeaderName(crew.getCrewLeader().getUsername())
            .crewLocation(toCrewLocationResponse(crew.getCrewLocation()))
            .crewRank(crew.getCrewRank())
            .crewUserRole(crewUserRole.toString())
            .build();
    }

    public LocationResponse toCrewLocationResponse(Location crewLocation) {
        return LocationResponse.builder()
            .country(crewLocation.getCountry())
            .province(crewLocation.getProvince())
            .city(crewLocation.getCity())
            .dong(crewLocation.getDong())
            .specificLocation(crewLocation.getSpecificLocation())
            .build();
    }

    public CrewSimpleResponse toCrewSimpleResponse(Crew crew) {
        return CrewSimpleResponse.builder()
            .name(crew.getName())
            .description(crew.getDescription())
            .userCount(crew.getUserCount())
            .image(crew.getImage())
            .build();
    }

    public Set<CrewParticipantsDetailsResponse> toCrewParticipantsDetailsResponse(
        Set<CrewParticipant> crewParticipants) {
        return crewParticipants.stream()
            .map(participant -> CrewParticipantsDetailsResponse.builder()
                .username(participant.getParticipant().getUsername())
                .build())
            .collect(Collectors.toSet());
    }

    public Page<CrewSimpleResponse> toCrewSimplePagingResponse(Page<Crew> crewPage) {
        return crewPage.map(this::toCrewSimpleResponse);
    }

    public CrewSearchCondition toCrewSearchCondition(CrewSearchRequest crewSearchRequest) {
        return CrewSearchCondition.builder()
            .region(crewSearchRequest.region())
            .name(crewSearchRequest.name())
            .build();
    }
}
