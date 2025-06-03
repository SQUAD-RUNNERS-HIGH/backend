package runnershigh.capstone.chat.service.message.mapper;

import org.springframework.stereotype.Component;
import runnershigh.capstone.chat.domain.ChatMessage;
import runnershigh.capstone.chat.domain.ChatMessageType;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.ChatMessageRequest;
import runnershigh.capstone.chat.dto.ChatMessageResponse;

@Component
public class ChatMessageMapper {

    public ChatMessage toChatMessage(ChatRoom room, ChatMessageRequest messageReq,
        Long userId) {
        return ChatMessage.builder()
            .chatRoom(room)
            .senderId(userId)
            .content(messageReq.content())
            .chatMessageType(ChatMessageType.valueOf(messageReq.messageType()))
            .build();
    }

    public ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
            .crewId(chatMessage.getChatRoom().getCrew().getId())
            .senderId(chatMessage.getSenderId())
            .content(chatMessage.getContent())
            .sentAt(chatMessage.getSentAt())
            .messageType(chatMessage.getChatMessageType().toString())
            .build();
    }
}
