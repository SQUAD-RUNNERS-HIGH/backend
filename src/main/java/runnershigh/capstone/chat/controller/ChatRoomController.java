package runnershigh.capstone.chat.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.chat.dto.response.ChatMessageListResponse;
import runnershigh.capstone.chat.dto.response.ChatRoomPreviewListResponse;
import runnershigh.capstone.chat.service.ChatService;
import runnershigh.capstone.global.argumentresolver.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("/all")
    public ChatRoomPreviewListResponse getChatRoomPreviews(
        @Parameter(hidden = true) @AuthUser Long userId) {
        return chatService.getChatRoomPreviews(userId);
    }

    @GetMapping("/{roomId}")
    public ChatMessageListResponse getChatMessages(@PathVariable Long roomId) {
        return chatService.getChatMessages(roomId);
    }

}
