package runnershigh.capstone.chat.service.room;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.chat.domain.ChatMessage;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.ChatRoomListResponse;
import runnershigh.capstone.chat.exception.ChatNotFoundException;
import runnershigh.capstone.chat.repository.ChatRoomRepository;
import runnershigh.capstone.chat.service.room.mapper.ChatRoomMapper;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.service.CrewQueryService;
import runnershigh.capstone.global.error.ErrorCode;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;
    private final CrewQueryService crewQueryService;

    @Transactional(readOnly = true)
    public ChatRoomListResponse getChatRoomList(Long userId) {
        List<Crew> crews = crewQueryService.getCrewsByUserId(userId);

        List<ChatRoom> chatRooms = crews.stream()
            .map(Crew::getChatRoom)
            .filter(Objects::nonNull)
            .toList();

        return chatRoomMapper.toChatRoomListResponse(chatRooms);
    }

    @Transactional(readOnly = true)
    public ChatRoom getChatRoom(Long crewId) {
        return chatRoomRepository.findById(crewId)
            .orElseThrow(() -> new ChatNotFoundException(ErrorCode.CHATROOM_NOT_FOUND)
            );
    }


    @Transactional
    public void updateLastMessageInfo(ChatRoom room, ChatMessage message) {
        room.saveLastChatTimeStampAndLastChat(message.getSentAt(), message.getContent());
        chatRoomRepository.save(room);
    }
}
