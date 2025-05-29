package runnershigh.capstone.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.chat.dto.ChatMessageRequest;
import runnershigh.capstone.chat.dto.ChatMessageResponse;
import runnershigh.capstone.chat.service.ChatService;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat/{crewId}")
    @SendTo("/topic/crew/{crewId}")
    public ChatMessageResponse sendToCrew(@DestinationVariable Long crewId,
        @Payload ChatMessageRequest messageRequest) {
        return chatService.processAndSaveChatMessage(crewId, messageRequest);
    }
}
