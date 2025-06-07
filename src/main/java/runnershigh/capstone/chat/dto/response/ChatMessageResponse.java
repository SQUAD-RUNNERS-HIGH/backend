package runnershigh.capstone.chat.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChatMessageResponse(
    Long crewId,
    Long senderId,
    String content,
    String messageType,
    LocalDateTime sentAt
) {

}
