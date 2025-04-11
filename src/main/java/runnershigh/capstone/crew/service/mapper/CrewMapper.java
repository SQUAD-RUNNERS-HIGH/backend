package runnershigh.capstone.crew.service.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.domain.CrewLocation;
import runnershigh.capstone.crew.dto.CrewCreateRequest;
import runnershigh.capstone.crew.dto.CrewDetailResponse;
import runnershigh.capstone.crew.dto.CrewLocationResponse;
import runnershigh.capstone.crew.dto.CrewParticipantsDetailsResponse;
import runnershigh.capstone.crew.dto.CrewSearchCondition;
import runnershigh.capstone.crew.dto.CrewSearchRequest;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.geocoding.dto.FormattedAddressResponse;
import runnershigh.capstone.user.domain.User;

@Component
public class CrewMapper {

    public Crew toCrew(User crewLeader, CrewCreateRequest crewCreateRequest,
        FormattedAddressResponse formattedAddressResponse) {
        return Crew.builder()
            .name(crewCreateRequest.name())
            .description(crewCreateRequest.description())
            .maxCapacity(crewCreateRequest.maxCapacity())
            .image(crewCreateRequest.image())
            .crewLocation(toCrewLocation(formattedAddressResponse))
            .crewLeader(crewLeader)
            .crewParticipant(new HashSet<>())
            .build();
    }

    public CrewLocation toCrewLocation(FormattedAddressResponse formattedAddressResponse) {
        return CrewLocation.builder()
            .country(formattedAddressResponse.country())
            .city(formattedAddressResponse.city())
            .province(formattedAddressResponse.province())
            .dong(formattedAddressResponse.dong())
            .build();
    }

    public CrewDetailResponse toCrewDetailResponse(Crew crew) {
        return CrewDetailResponse.builder()
            .name(crew.getName())
            .description(crew.getDescription())
            .maxCapacity(crew.getMaxCapacity())
            .userCount(crew.getUserCount())
            .image(crew.getImage())
            .crewLeaderName(crew.getCrewLeader().getUsername())
            .crewLocation(toCrewLocationResponse(crew.getCrewLocation()))
            .build();
    }

    public CrewLocationResponse toCrewLocationResponse(CrewLocation crewLocation) {
        return CrewLocationResponse.builder()
            .country(crewLocation.getCountry())
            .province(crewLocation.getProvince())
            .city(crewLocation.getCity())
            .dong(crewLocation.getDong())
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

    public Page<CrewDetailResponse> toCrewSearchResponse(Page<Crew> crewPage) {
        return crewPage.map(this::toCrewDetailResponse);
    }

    public CrewSearchCondition toCrewSearchCondition(CrewSearchRequest crewSearchRequest) {
        return CrewSearchCondition.builder()
            .region(crewSearchRequest.region())
            .name(crewSearchRequest.name())
            .build();
    }


}
