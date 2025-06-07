package runnershigh.capstone.chat.dto.response;

public record ChatMessageWithSenderResponse(
    ChatMessageResponse chatMessageResponse,
    String senderName
) {

}
