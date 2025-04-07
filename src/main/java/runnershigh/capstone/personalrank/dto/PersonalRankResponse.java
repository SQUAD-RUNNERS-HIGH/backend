package runnershigh.capstone.personalrank.dto;

public record PersonalRankResponse(
    String userName,
    Double runningTime,
    String historyId
) {
}
