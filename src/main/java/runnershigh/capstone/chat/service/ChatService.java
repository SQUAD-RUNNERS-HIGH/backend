package runnershigh.capstone.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.ChatMessageRequest;
import runnershigh.capstone.chat.dto.ChatMessageResponse;
import runnershigh.capstone.chat.service.message.ChatMessageService;
import runnershigh.capstone.chat.service.room.ChatRoomService;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    public ChatMessageResponse processAndSaveChatMessage(Long crewId,
        ChatMessageRequest chatMessageRequest) {
        ChatRoom room = chatRoomService.getChatRoom(crewId);
        return chatMessageService.saveChatMessage(room, chatMessageRequest);
    }
}
