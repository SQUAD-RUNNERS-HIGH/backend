package runnershigh.capstone.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.chat.domain.ChatMessage;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.ChatMessageRequest;
import runnershigh.capstone.chat.dto.ChatMessageResponse;
import runnershigh.capstone.chat.service.message.ChatMessageService;
import runnershigh.capstone.chat.service.message.mapper.ChatMessageMapper;
import runnershigh.capstone.chat.service.room.ChatRoomService;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatMessageMapper chatMessageMapper;

    @Transactional
    public ChatMessageResponse sendChatMessage(Long crewId, ChatMessageRequest messageReq,
        Long userId) {
        ChatMessage message = saveChatMessage(crewId, messageReq, userId);
        return chatMessageMapper.toChatMessageResponse(message);
    }

    private ChatMessage saveChatMessage(Long crewId, ChatMessageRequest messageReq, Long userId) {
        ChatRoom room = chatRoomService.getChatRoom(crewId);
        room.validationCrewParticipant(userId);

        ChatMessage message = chatMessageService.saveMessage(room, messageReq, userId);
        chatRoomService.updateLastMessageInfo(room, message);

        return message;
    }
}
