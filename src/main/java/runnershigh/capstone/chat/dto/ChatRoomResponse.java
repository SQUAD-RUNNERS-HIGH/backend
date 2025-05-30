package runnershigh.capstone.chat.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChatRoomResponse(
    String name,
    int userCount,
    LocalDateTime lastChatTimeStamp,
    String lastChat
) {

}
