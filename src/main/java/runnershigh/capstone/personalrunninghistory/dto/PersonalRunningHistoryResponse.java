package runnershigh.capstone.personalrunninghistory.dto;

import java.util.List;

public record PersonalRunningHistoryResponse(
    List<List<Double>> progress,
    double runningTime
) {

}
