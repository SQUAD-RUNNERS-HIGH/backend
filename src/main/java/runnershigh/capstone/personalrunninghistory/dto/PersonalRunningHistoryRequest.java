package runnershigh.capstone.personalrunninghistory.dto;

import java.util.List;

public record PersonalRunningHistoryRequest(
    List<Double> progress,
    double runningTime,
    String courseId
) {

}
