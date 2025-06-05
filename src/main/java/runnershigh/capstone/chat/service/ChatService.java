package runnershigh.capstone.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.chat.domain.ChatMessage;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.request.ChatMessageRequest;
import runnershigh.capstone.chat.dto.response.ChatMessageListResponse;
import runnershigh.capstone.chat.dto.response.ChatMessageResponse;
import runnershigh.capstone.chat.dto.response.ChatRoomPreviewListResponse;
import runnershigh.capstone.chat.service.message.ChatMessageService;
import runnershigh.capstone.chat.service.room.ChatRoomService;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    public ChatMessageResponse sendChatMessage(Long crewId, ChatMessageRequest messageReq,
        Long userId) {
        ChatRoom room = chatRoomService.getChatRoom(crewId);
        room.validationCrewParticipant(userId);

        ChatMessage message = chatMessageService.saveMessage(room, messageReq, userId);
        chatRoomService.updateLastMessageInfo(room, message);

        return chatMessageService.toChatMessageResponse(message);
    }

    public ChatMessageListResponse getChatMessages(Long roomId) {
        return chatMessageService.getChatMessages(roomId);
    }

    public ChatRoomPreviewListResponse getChatRoomPreviews(Long roomId) {
        return chatRoomService.getChatRoomPreviews(roomId);
    }
}
