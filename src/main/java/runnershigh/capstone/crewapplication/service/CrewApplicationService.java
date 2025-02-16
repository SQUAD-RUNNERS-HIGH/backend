package runnershigh.capstone.crewapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.service.CrewService;
import runnershigh.capstone.crewapplication.domain.CrewApplication;
import runnershigh.capstone.crewapplication.dto.CrewApplicationApprovalResponse;
import runnershigh.capstone.crewapplication.dto.CrewApplicationRefusalResponse;
import runnershigh.capstone.crewapplication.dto.CrewApplicationResponse;
import runnershigh.capstone.crewapplication.exception.CrewApplicationNotFoundException;
import runnershigh.capstone.crewapplication.repository.CrewApplicationRepository;
import runnershigh.capstone.crewapplication.service.mapper.CrewApplicationMapper;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional
public class CrewApplicationService {

    private final UserService userService;
    private final CrewService crewService;
    private final CrewApplicationMapper crewApplicationMapper;
    private final CrewApplicationRepository crewApplicationRepository;

    public CrewApplicationResponse apply(Long applicantId, Long crewId) {

        validateDuplicatedApplication(applicantId, crewId);
        User applicant = userService.getUser(applicantId);
        Crew crew = crewService.getCrewById(crewId);
        crew.validationCrewParticipant(applicantId);
        CrewApplication crewApplication = crewApplicationMapper.toCrewApplication(applicant, crew);
        crewApplicationRepository.save(crewApplication);

        return new CrewApplicationResponse(crewApplication.getId());
    }

    public CrewApplicationApprovalResponse approve(Long crewLeaderId, Long applicantId,
        Long crewId) {
        CrewApplication crewApplication = getCrewApplication(applicantId, crewId);
        crewApplication.approve(crewLeaderId);
        return new CrewApplicationApprovalResponse();
    }

    public CrewApplicationRefusalResponse refuse(Long crewLeaderId, Long applicantId, Long crewId) {
        CrewApplication crewApplication = getCrewApplication(applicantId, crewId);
        crewApplication.refuse(crewLeaderId);
        return new CrewApplicationRefusalResponse();
    }

    private CrewApplication getCrewApplication(Long applicantId, Long crewId) {
        return crewApplicationRepository.findByApplicantIdAndCrewId(
                applicantId, crewId)
            .orElseThrow(
                () -> new CrewApplicationNotFoundException(ErrorCode.CREW_APPLICATION_NOT_FOUND));
    }

    private void validateDuplicatedApplication(Long applicantId, Long crewId) {
        if (crewApplicationRepository.existsByApplicantIdAndCrewId(applicantId, crewId)) {
            throw new CrewApplicationNotFoundException(ErrorCode.CREW_APPLICATION_DUPLICATED);
        }
    }

}
