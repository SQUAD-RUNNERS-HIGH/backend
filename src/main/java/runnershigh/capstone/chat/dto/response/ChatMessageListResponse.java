package runnershigh.capstone.chat.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record ChatMessageListResponse(
    List<ChatMessageWithSenderResponse> ChatMessageWithSenderResponse
) {

}
