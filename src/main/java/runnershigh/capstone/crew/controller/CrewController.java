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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
import runnershigh.capstone.crew.service.CrewQueryService;
import runnershigh.capstone.crew.service.CrewService;
import runnershigh.capstone.global.argumentresolver.AuthUser;

@RestController
@RequestMapping("/api/crew")
@RequiredArgsConstructor
@Tag(name = "크루 [크루 CRUD & 위치]")
public class CrewController {

    private final CrewService crewService;
    private final CrewQueryService crewQueryService;

    @PostMapping
    @Operation(summary = "크루 생성", description = "크루 리더 ID & 크루 생성 정보를 받아, 크루 ID를 반환합니다.")
    public CrewCreateResponse createCrew(@Parameter(hidden = true) @AuthUser Long crewLeaderId,
        @RequestPart CrewCreateRequest crewCreateRequest, @RequestPart MultipartFile image) {
        return crewService.createCrew(crewLeaderId, crewCreateRequest, image);
    }

    @GetMapping("/{crewId}")
    @Operation(summary = "크루 정보 조회", description = "크루 ID를 받아, 크루 관련 정보들을 반환합니다.")
    public CrewDetailResponse getCrewDetail(@Parameter(hidden = true) @AuthUser Long userId,
        @PathVariable Long crewId) {
        return crewQueryService.getCrewDetail(userId, crewId);
    }

    @GetMapping("/{crewId}/participants")
    @Operation(summary = "크루 참가자들 조회", description = "크루 ID를 받아, 해당 크루 참가자들의 정보들을 반환합니다.")
    public Set<CrewParticipantsDetailsResponse> getCrewParticipants(@PathVariable Long crewId) {
        return crewQueryService.getCrewParticipants(crewId);
    }

    @PatchMapping("/{crewId}")
    @Operation(summary = "크루 정보 수정", description = "크루 리더 ID & 크루 수정 정보를 받아, 크루 ID를 반환합니다.")
    public CrewUpdateResponse updateCrew(
        @Parameter(hidden = true) @AuthUser Long crewLeaderId,
        @RequestPart CrewUpdateRequest crewUpdateRequest,
        @PathVariable Long crewId,
        @RequestPart MultipartFile image) {
        return crewService.updateCrew(crewLeaderId, crewUpdateRequest, crewId, image);
    }

    @GetMapping("/search")
    @Operation(summary = "크루 검색", description = "검색할 정보를 받아, 해당하는 크루들의 정보를 반환합니다.")
    public CrewSearchPagingResponse<CrewSimpleResponse> searchCrew(
        @ModelAttribute CrewSearchRequest crewSearchRequest, Pageable pageable) {
        return crewQueryService.searchCrew(crewSearchRequest, pageable);
    }

    @GetMapping("/nearby")
    @Operation(summary = "주변 크루 검색", description = "유저 ID를 받아, 주변 크루들의 정보를 반환합니다.")
    public CrewSearchPagingResponse<CrewSimpleResponse> getNearbyCrews(
        @Parameter(hidden = true) @AuthUser Long userId,
        Pageable pageable) {
        return crewQueryService.getCrewNearby(userId, pageable);
    }

    @DeleteMapping("/{crewId}")
    @Operation(summary = "크루 삭제", description = "크루 리더 ID와 크루 ID를 받아, 삭제된 크루 ID를 반환합니다.")
    public CrewDeleteResponse deleteCrew(@Parameter(hidden = true) @AuthUser Long crewLeaderId,
        @PathVariable Long crewId) {
        return crewService.deleteCrew(crewLeaderId, crewId);
    }
}
