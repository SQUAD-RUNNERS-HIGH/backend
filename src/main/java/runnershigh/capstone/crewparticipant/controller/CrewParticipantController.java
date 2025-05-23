package runnershigh.capstone.crewparticipant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.crewparticipant.dto.CrewParticipantDeleteResponse;
import runnershigh.capstone.crewparticipant.dto.MyCrewResponse;
import runnershigh.capstone.crewparticipant.service.CrewParticipantService;
import runnershigh.capstone.global.argumentresolver.AuthUser;

@RestController
@RequestMapping("/api/crew-participant/crew")
@RequiredArgsConstructor
@Tag(name = "크루 참가자 [크루 참가자 관련 기능]")
public class CrewParticipantController {

    private final CrewParticipantService crewParticipantService;

    @DeleteMapping("/{crewId}")
    @Operation(summary = "크루 참가자 탈퇴", description = "크루 ID를 요청받아, 탈퇴한 크루 참가자 ID를 반환한다.")
    public CrewParticipantDeleteResponse withdrawCrewParticipant(
        @Parameter(hidden = true) @AuthUser Long participantId,
        @PathVariable Long crewId) {
        return crewParticipantService.withdrawCrewParticipant(participantId, crewId);
    }

    @DeleteMapping("/{crewId}/participant/{participantId}")
    @Operation(summary = "크루 참가자 추방", description = "크루 ID & 추방할 참가자 ID를 받아, 추방한 크루 참가자 ID를 반환한다.")
    public CrewParticipantDeleteResponse kickCrewParticipant(
        @Parameter(hidden = true) @AuthUser Long crewLeaderId,
        @PathVariable Long crewId, @PathVariable Long participantId) {
        return crewParticipantService.kickCrewParticipant(crewLeaderId, crewId, participantId);
    }

    @GetMapping
    @Operation(summary = "나의 크루 조회", description = "사용자가 가입된 크루를 조회합니다.")
    public MyCrewResponse findMyCrews(@Parameter(hidden = true) @AuthUser Long userId){
        return crewParticipantService.findMyCrews(userId);
    }
}
