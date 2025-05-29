package runnershigh.capstone.chat.dto;

public record ChatMessageRequest(
    Long senderId,
    String senderName,
    String content,
    String messageType
) {

}
