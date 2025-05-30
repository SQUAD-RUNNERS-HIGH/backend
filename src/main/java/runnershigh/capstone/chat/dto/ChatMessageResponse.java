package runnershigh.capstone.chat.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChatMessageResponse(
    Long crewId,
    Long senderId,
    String senderName,
    String content,
    String messageType,
    LocalDateTime sentAt
) {

}
