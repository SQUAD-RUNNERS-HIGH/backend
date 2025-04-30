package runnershigh.capstone.crewapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.crewapplication.dto.CrewApplicationApprovalResponse;
import runnershigh.capstone.crewapplication.dto.CrewApplicationRefusalResponse;
import runnershigh.capstone.crewapplication.dto.CrewApplicationResponse;
import runnershigh.capstone.crewapplication.dto.CrewApplicationsListResponse;
import runnershigh.capstone.crewapplication.service.CrewApplicationService;
import runnershigh.capstone.global.argumentresolver.AuthUser;

@RestController
@RequestMapping("/api/crew-applicant/crew")
@RequiredArgsConstructor
@Tag(name = "크루 지원 [승인 & 거절]")
public class CrewApplicationController {

    private final CrewApplicationService crewApplicationService;

    @PostMapping("/{crewId}")
    @Operation(summary = "크루 지원", description = "크루 지원자 ID & 크루 ID를 받아, 지원한 크루 ID를 반환합니다.")
    public CrewApplicationResponse apply(@Parameter(hidden = true) @AuthUser Long applicantId,
        @PathVariable Long crewId) {
        return crewApplicationService.apply(applicantId, crewId);
    }

    @GetMapping("/{crewId}")
    @Operation(summary = "크루 지원자들 조회", description = "크루 리더 ID & 크루 ID를 받아, 해당 크루 지원자들의 정보를 반환합니다.")
    public CrewApplicationsListResponse getCrewApplicants(
        @Parameter(hidden = true) @AuthUser Long crewLeaderId, @PathVariable Long crewId) {
        return crewApplicationService.getCrewApplicants(crewLeaderId, crewId);
    }

    @PostMapping("/{crewId}/applicant/{applicantId}")
    @Operation(summary = "크루 지원 승인", description = "크루 리더 ID & 크루 지원자 ID & 크루 ID를 받아, ~~를 반환합니다.")
    public CrewApplicationApprovalResponse approve(
        @Parameter(hidden = true) @AuthUser Long crewLeaderId,
         @PathVariable Long crewId, @PathVariable Long applicantId) {
        return crewApplicationService.approve(crewLeaderId, applicantId, crewId);
    }

    @PatchMapping("/{crewId}/applicant/{applicantId}")
    @Operation(summary = "크루 지원 거절", description = "크루 리더 ID & 크루 지원자 ID & 크루 ID를 받아, ~~를 반환합니다.")
    public CrewApplicationRefusalResponse refuse(
        @Parameter(hidden = true) @AuthUser Long crewLeaderId,
        @PathVariable Long applicantId, @PathVariable Long crewId) {
        return crewApplicationService.refuse(crewLeaderId, applicantId, crewId);
    }


}
