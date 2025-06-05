package runnershigh.capstone.chat.service.message;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import runnershigh.capstone.chat.domain.ChatMessage;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.request.ChatMessageRequest;
import runnershigh.capstone.chat.dto.response.ChatMessageListResponse;
import runnershigh.capstone.chat.dto.response.ChatMessageResponse;
import runnershigh.capstone.chat.repository.ChatMessageRepository;
import runnershigh.capstone.chat.service.message.mapper.ChatMessageMapper;
import runnershigh.capstone.user.service.UserQueryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final UserQueryService userQueryService;

    public ChatMessage saveMessage(ChatRoom room,
        ChatMessageRequest messageReq, Long userId) {
        ChatMessage message = chatMessageMapper.toChatMessage(room, messageReq, userId);
        return chatMessageRepository.save(message);
    }

    public ChatMessageListResponse getChatMessages(Long roomId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomIdOrderBySentAtAsc(
            roomId);

        Set<Long> senderIds = chatMessages.stream()
            .map(ChatMessage::getSenderId)
            .collect(Collectors.toSet());

        Map<Long, String> senderNames = userQueryService.getUsernamesByIds(senderIds);
        return chatMessageMapper.toChatMessageListResponse(chatMessages, senderNames);
    }

    public ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage) {
        return chatMessageMapper.toChatMessageResponse(chatMessage);
    }
}
