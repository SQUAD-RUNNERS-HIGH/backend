package runnershigh.capstone.chat.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.chat.dto.ChatRoomListResponse;
import runnershigh.capstone.chat.service.room.ChatRoomService;
import runnershigh.capstone.global.argumentresolver.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public ChatRoomListResponse getChatRooms(@Parameter(hidden = true) @AuthUser Long userId) {
        return chatRoomService.getChatRoomList(userId);
    }
}
