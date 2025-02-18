package runnershigh.capstone.crewparticipant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.crewparticipant.dto.CrewParticipantDeleteResponse;
import runnershigh.capstone.crewparticipant.exception.CrewParticipantNotFoundException;
import runnershigh.capstone.crewparticipant.repository.CrewParticipantRepository;
import runnershigh.capstone.global.error.ErrorCode;

@Service
@RequiredArgsConstructor
public class CrewParticipantService {

    private final CrewParticipantRepository crewParticipantRepository;

    @Transactional
    public CrewParticipantDeleteResponse withdrawCrewParticipant(Long participantId, Long crewId) {

        CrewParticipant crewParticipant = getCrewParticipant(participantId, crewId);
        crewParticipantRepository.delete(crewParticipant);

        return new CrewParticipantDeleteResponse(crewParticipant.getParticipant().getId());
    }

    @Transactional
    public CrewParticipantDeleteResponse kickCrewParticipant(Long crewLeaderId, Long crewId,
        Long participantId) {

        CrewParticipant crewParticipant = getCrewParticipant(participantId, crewId);
        crewParticipant.getCrew().validationCrewLeader(crewLeaderId);
        crewParticipantRepository.delete(crewParticipant);

        return new CrewParticipantDeleteResponse(crewParticipant.getParticipant().getId());
    }

    private CrewParticipant getCrewParticipant(Long participantId, Long crewId) {
        return crewParticipantRepository.findByParticipantIdAndCrewId(
            participantId, crewId).orElseThrow(() -> new CrewParticipantNotFoundException(
            ErrorCode.CREW_PARTICIPANT_NOT_FOUND));
    }
}
