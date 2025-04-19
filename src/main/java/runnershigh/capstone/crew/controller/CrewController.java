package runnershigh.capstone.crew.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.crew.dto.CrewCreateRequest;
import runnershigh.capstone.crew.dto.CrewCreateResponse;
import runnershigh.capstone.crew.dto.CrewDeleteResponse;
import runnershigh.capstone.crew.dto.CrewDetailResponse;
import runnershigh.capstone.crew.dto.CrewNearbyResponse;
import runnershigh.capstone.crew.dto.CrewParticipantsDetailsResponse;
import runnershigh.capstone.crew.dto.CrewSearchRequest;
import runnershigh.capstone.crew.dto.CrewSearchResponse;
import runnershigh.capstone.crew.dto.CrewUpdateRequest;
import runnershigh.capstone.crew.dto.CrewUpdateResponse;
import runnershigh.capstone.crew.service.CrewService;
import runnershigh.capstone.global.argumentresolver.AuthUser;

@RestController
@RequestMapping("/api/crew")
@RequiredArgsConstructor
@Tag(name = "크루 [크루 CRUD & 위치]")
public class CrewController {

    private final CrewService crewService;

    @PostMapping
    @Operation(summary = "크루 생성", description = "크루 리더 ID & 크루 생성 정보를 받아, 크루 ID를 반환합니다.")
    public CrewCreateResponse createCrew(@Parameter(hidden = true) @AuthUser Long crewLeaderId,
        @RequestBody CrewCreateRequest crewCreateRequest) {
        return crewService.createCrew(crewLeaderId, crewCreateRequest);
    }

    @GetMapping("/{crewId}")
    @Operation(summary = "크루 조회", description = "크루 ID를 받아, 크루 관련 정보들을 반환합니다.")
    public CrewDetailResponse getCrewDetail(@PathVariable Long crewId) {
        return crewService.getCrewDetail(crewId);
    }

    @GetMapping("/{crewId}/participants")
    @Operation(summary = "크루 참가자들 조회", description = "크루 ID를 받아, 해당 크루 참가자들의 정보들을 반환합니다.")
    public Set<CrewParticipantsDetailsResponse> getCrewParticipants(@PathVariable Long crewId) {
        return crewService.getCrewParticipants(crewId);
    }

    @PatchMapping
    @Operation(summary = "크루 정보 수정", description = "크루 리더 ID & 크루 수정 정보를 받아, 크루 ID를 반환합니다.")
    public CrewUpdateResponse updateCrew(@Parameter(hidden = true) @AuthUser Long crewLeaderId,
        @RequestBody CrewUpdateRequest crewUpdateRequest) {
        return crewService.updateCrew(crewLeaderId, crewUpdateRequest);
    }

    @GetMapping("/search")
    @Operation(summary = "크루 검색", description = "검색할 정보를 받아, 해당하는 크루들의 정보를 반환합니다.")
    public CrewSearchResponse<CrewDetailResponse> searchCrew(
        @ModelAttribute CrewSearchRequest crewSearchRequest, Pageable pageable) {
        return crewService.searchCrew(crewSearchRequest, pageable);
    }

    @GetMapping("/nearby")
    @Operation(summary = "주변 크루 검색", description = "유저 ID를 받아, 주변 크루들의 정보를 반환합니다.")
    public CrewSearchResponse<CrewNearbyResponse> getNearbyCrews(
        @Parameter(hidden = true) @AuthUser Long userId,
        Pageable pageable) {
        return crewService.getCrewNearby(userId, pageable);
    }

    @DeleteMapping
    @Operation(summary = "크루 삭제", description = "크루 리더 ID를 받아, 삭제된 크루 ID를 반환합니다.")
    public CrewDeleteResponse deleteCrew(@Parameter(hidden = true) @AuthUser Long crewLeaderId) {
        return crewService.deleteCrew(crewLeaderId);
    }
}
