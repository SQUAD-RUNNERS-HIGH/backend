package runnershigh.capstone.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "채팅방 [채팅방 조회]")
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("/all")
    @Operation(summary = "유저 채팅방 전체 조회", description = "유저 ID를 받아, 유저가 참여되어있는 채팅방 전체 조회.")
    public ChatRoomPreviewListResponse getChatRoomPreviews(
        @Parameter(hidden = true) @AuthUser Long userId) {
        return chatService.getChatRoomPreviews(userId);
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "채팅방 내역 조회", description = "채팅방 ID를 받아, 채팅방의 내역 조회.")
    public ChatMessageListResponse getChatMessages(@PathVariable Long roomId) {
        return chatService.getChatMessages(roomId);
    }

}
