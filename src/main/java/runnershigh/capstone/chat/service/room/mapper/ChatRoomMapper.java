package runnershigh.capstone.chat.service.room.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import runnershigh.capstone.chat.domain.ChatRoom;
import runnershigh.capstone.chat.dto.response.ChatRoomPreviewListResponse;
import runnershigh.capstone.chat.dto.response.ChatRoomPreviewResponse;

@Component
public class ChatRoomMapper {

    public ChatRoomPreviewListResponse toChatRoomPreviewListResponse(List<ChatRoom> chatRooms) {
        List<ChatRoomPreviewResponse> responses = chatRooms.stream()
            .map(this::toChatRoomPreviewResponse)
            .collect(Collectors.toList());

        return new ChatRoomPreviewListResponse(responses);
    }

    public ChatRoomPreviewResponse toChatRoomPreviewResponse(ChatRoom chatRoom) {
        return ChatRoomPreviewResponse.builder()
            .name(chatRoom.getCrew().getName())
            .userCount(chatRoom.getCrew().getUserCount())
            .lastChatTimeStamp(chatRoom.getLastChatTimeStamp())
            .lastChat(chatRoom.getLastChat())
            .build();
    }

}
