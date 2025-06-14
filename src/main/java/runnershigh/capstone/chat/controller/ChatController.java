package runnershigh.capstone.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.chat.dto.request.ChatMessageRequest;
import runnershigh.capstone.chat.dto.response.ChatMessageResponse;
import runnershigh.capstone.chat.service.ChatService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat/crew/{crewId}")
    @SendTo("/topic/chat/crew/{crewId}")
    public ChatMessageResponse sendToCrew(@DestinationVariable Long crewId,
        @Payload ChatMessageRequest messageRequest, SimpMessageHeaderAccessor headerAccessor) {
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        return chatService.sendChatMessage(crewId, messageRequest, userId);
    }
}
