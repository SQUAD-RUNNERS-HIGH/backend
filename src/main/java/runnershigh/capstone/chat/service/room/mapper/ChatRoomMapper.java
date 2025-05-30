package runnershigh.capstone.chat.service.room.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.ChatRoomListResponse;
import runnershigh.capstone.chat.dto.ChatRoomResponse;

@Component
public class ChatRoomMapper {

    public ChatRoomListResponse toChatRoomListResponse(List<ChatRoom> chatRooms) {
        List<ChatRoomResponse> responses = chatRooms.stream()
            .map(this::toChatRoomResponse)
            .collect(Collectors.toList());

        return new ChatRoomListResponse(responses);
    }

    public ChatRoomResponse toChatRoomResponse(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
            .name(chatRoom.getCrew().getName())
            .userCount(chatRoom.getCrew().getUserCount())
            .lastChatTimeStamp(chatRoom.getLastChatTimeStamp())
            .lastChat(chatRoom.getLastChat())
            .build();
    }

}
