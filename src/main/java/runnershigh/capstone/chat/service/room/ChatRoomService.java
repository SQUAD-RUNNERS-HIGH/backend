package runnershigh.capstone.chat.service.room;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.ChatRoomListResponse;
import runnershigh.capstone.chat.exception.ChatNotFoundException;
import runnershigh.capstone.chat.repository.ChatRoomRepository;
import runnershigh.capstone.chat.service.room.mapper.ChatRoomMapper;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.crewparticipant.repository.CrewParticipantRepository;
import runnershigh.capstone.global.error.ErrorCode;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final CrewParticipantRepository crewParticipantRepository;

    private final ChatRoomMapper chatRoomMapper;

    @Transactional(readOnly = true)
    public ChatRoom getChatRoom(Long crewId) {
        return chatRoomRepository.findById(crewId)
            .orElseThrow(() -> new ChatNotFoundException(ErrorCode.CHATROOM_NOT_FOUND)
            );
    }

    @Transactional(readOnly = true)
    public ChatRoomListResponse getChatRoomList(Long userId) {
        List<CrewParticipant> crewParticipants = crewParticipantRepository.findByUserId(userId);

        List<ChatRoom> chatRooms = crewParticipants.stream()
            .map(participation -> participation.getCrew().getChatRoom())
            .filter(Objects::nonNull)
            .toList();

        return chatRoomMapper.toChatRoomListResponse(chatRooms);
    }
}
