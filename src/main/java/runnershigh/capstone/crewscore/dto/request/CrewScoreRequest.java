package runnershigh.capstone.crewscore.dto.request;

public record CrewScoreRequest(
    Integer numberOfRunners,
    String courseId,
    Long crewId
)  {

}
