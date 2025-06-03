package runnershigh.capstone.chat.service.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import runnershigh.capstone.chat.domain.ChatMessage;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.ChatMessageRequest;
import runnershigh.capstone.chat.repository.ChatMessageRepository;
import runnershigh.capstone.chat.service.message.mapper.ChatMessageMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    public ChatMessage saveMessage(ChatRoom room,
        ChatMessageRequest messageReq, Long userId) {
        ChatMessage message = chatMessageMapper.toChatMessage(room, messageReq, userId);
        return chatMessageRepository.save(message);
    }
}
