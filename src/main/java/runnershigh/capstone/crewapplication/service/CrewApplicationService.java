package runnershigh.capstone.crewapplication.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.service.CrewQueryService;
import runnershigh.capstone.crewapplication.domain.CrewApplication;
import runnershigh.capstone.crewapplication.domain.CrewApplicationStatus;
import runnershigh.capstone.crewapplication.dto.CrewApplicationApprovalResponse;
import runnershigh.capstone.crewapplication.dto.CrewApplicationRefusalResponse;
import runnershigh.capstone.crewapplication.dto.CrewApplicationResponse;
import runnershigh.capstone.crewapplication.dto.CrewApplicationsListResponse;
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
    private final CrewApplicationMapper crewApplicationMapper;
    private final CrewApplicationRepository crewApplicationRepository;
    private final CrewQueryService crewQueryService;


    public CrewApplicationResponse apply(Long applicantId, Long crewId) {

        validateDuplicatedApplication(applicantId, crewId);
        User applicant = userService.getUser(applicantId);
        Crew crew = crewQueryService.getCrewById(crewId);
        crew.validationNotCrewParticipant(applicantId);
        CrewApplication crewApplication = crewApplicationMapper.toCrewApplication(applicant, crew);
        crewApplicationRepository.save(crewApplication);

        return new CrewApplicationResponse(crewApplication.getId());
    }

    public CrewApplicationApprovalResponse approve(Long crewLeaderId, Long applicantId,
        Long crewId) {
        CrewApplication crewApplication = getCrewApplicationByApplicant(applicantId, crewId);
        crewApplication.approve(crewLeaderId);
        crewApplicationRepository.delete(crewApplication);
        return new CrewApplicationApprovalResponse(applicantId);
    }

    public CrewApplicationRefusalResponse refuse(Long crewLeaderId, Long applicantId, Long crewId) {
        CrewApplication crewApplication = getCrewApplicationByApplicant(applicantId, crewId);
        crewApplication.refuse(crewLeaderId);
        crewApplicationRepository.delete(crewApplication);
        return new CrewApplicationRefusalResponse();
    }

    public CrewApplicationsListResponse getCrewApplicants(Long crewLeaderId, Long crewId) {
        Crew crew = crewQueryService.getCrewById(crewId);
        crew.validationCrewLeader(crewLeaderId);
        List<CrewApplication> crewApplications = getCrewApplications(crewId);
        return crewApplicationMapper.toCrewApplicationsListResponse(crewApplications);
    }

    private CrewApplication getCrewApplicationByApplicant(Long applicantId, Long crewId) {
        return crewApplicationRepository.findByApplicantIdAndCrewId(
                applicantId, crewId)
            .orElseThrow(
                () -> new CrewApplicationNotFoundException(ErrorCode.CREW_APPLICATION_NOT_FOUND));
    }

    private List<CrewApplication> getCrewApplications(Long crewId) {
        return crewApplicationRepository.findCrewApplicationsByCrewIdAndStatus(crewId,
            CrewApplicationStatus.PENDING);
    }

    private void validateDuplicatedApplication(Long applicantId, Long crewId) {
        if (crewApplicationRepository.existsByApplicantIdAndCrewId(applicantId, crewId)) {
            throw new CrewApplicationNotFoundException(ErrorCode.CREW_APPLICATION_DUPLICATION);
        }
    }

}
