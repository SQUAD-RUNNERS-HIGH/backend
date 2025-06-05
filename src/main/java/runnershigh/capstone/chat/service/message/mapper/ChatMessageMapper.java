package runnershigh.capstone.chat.service.message.mapper;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import runnershigh.capstone.chat.domain.ChatMessage;
import runnershigh.capstone.chat.domain.ChatMessageType;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.request.ChatMessageRequest;
import runnershigh.capstone.chat.dto.response.ChatMessageListResponse;
import runnershigh.capstone.chat.dto.response.ChatMessageResponse;
import runnershigh.capstone.chat.dto.response.ChatMessageWithSenderResponse;

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

    public ChatMessageListResponse toChatMessageListResponse(List<ChatMessage> chatMessages,
        Map<Long, String> senderNames) {
        List<ChatMessageWithSenderResponse> responses = chatMessages.stream()
            .map(m -> new ChatMessageWithSenderResponse(
                    new ChatMessageResponse(
                        m.getId(),
                        m.getSenderId(),
                        m.getContent(),
                        m.getChatMessageType().name(),
                        m.getSentAt()
                    ), senderNames.getOrDefault(m.getSenderId(), "알 수 없음")
                )
            ).toList();

        return new ChatMessageListResponse(responses);
    }
}
