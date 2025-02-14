package runnershigh.capstone.crewapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.crewapplication.dto.CrewApplicationApprovalResponse;
import runnershigh.capstone.crewapplication.dto.CrewApplicationRefusalResponse;
import runnershigh.capstone.crewapplication.dto.CrewApplicationResponse;
import runnershigh.capstone.crewapplication.service.CrewApplicationService;
import runnershigh.capstone.global.argumentresolver.AuthUser;

@RestController
@RequestMapping("/crew/crew-applicant")
@RequiredArgsConstructor
public class CrewApplicationController {

    private final CrewApplicationService crewApplicationService;

    @PostMapping("/{crewId}")
    public CrewApplicationResponse apply(@AuthUser Long applicantId, @PathVariable Long crewId) {
        return crewApplicationService.apply(applicantId, crewId);
    }

    @PostMapping("/{crewId}/applicant/{applicantId}")
    public CrewApplicationApprovalResponse approve(@AuthUser Long crewLeaderId,
        @PathVariable Long applicantId, @PathVariable Long crewId) {
        return crewApplicationService.approve(crewLeaderId, applicantId, crewId);
    }

    @PatchMapping("/{crewId}/applicant/{applicantId}")
    public CrewApplicationRefusalResponse refuse(@AuthUser Long crewLeaderId,
        @PathVariable Long applicantId, @PathVariable Long crewId) {
        return crewApplicationService.refuse(crewLeaderId, crewId, applicantId);
    }
}
