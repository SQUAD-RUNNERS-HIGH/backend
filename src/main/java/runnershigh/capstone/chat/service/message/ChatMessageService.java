package runnershigh.capstone.chat.service.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import runnershigh.capstone.chat.domain.ChatMessage;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.ChatMessageRequest;
import runnershigh.capstone.chat.dto.ChatMessageResponse;
import runnershigh.capstone.chat.repository.ChatMessageRepository;
import runnershigh.capstone.chat.repository.ChatRoomRepository;
import runnershigh.capstone.chat.service.message.mapper.ChatMessageMapper;
import runnershigh.capstone.user.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageMapper chatMessageMapper;
    private final UserService userService;

    public ChatMessageResponse saveChatMessage(ChatRoom room,
        ChatMessageRequest chatMessageRequest, Long userId) {

        String senderName = userService.getUser(userId).getUsername();
        ChatMessage message = chatMessageMapper.toChatMessage(room, chatMessageRequest, userId,
            senderName);
        chatMessageRepository.save(message);

        room.saveLastChatTimeStampAndLastChat(message.getSentAt(), message.getContent());
        chatRoomRepository.save(room);

        return chatMessageMapper.toChatMessageResponse(message);
    }
}
