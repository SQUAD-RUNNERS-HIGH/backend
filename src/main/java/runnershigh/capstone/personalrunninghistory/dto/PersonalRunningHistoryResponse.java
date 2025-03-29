package runnershigh.capstone.personalrunninghistory.dto;

import java.util.List;

public record PersonalRunningHistoryResponse(
    List<Double> progress,
    double runningTime,
    String competitorUserName
) {

}
