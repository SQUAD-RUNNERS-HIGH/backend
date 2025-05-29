package runnershigh.capstone.chat.dto;

import java.util.List;

public record ChatRoomListResponse(
    List<ChatRoomResponse> chatRoomResponses
) {

}
