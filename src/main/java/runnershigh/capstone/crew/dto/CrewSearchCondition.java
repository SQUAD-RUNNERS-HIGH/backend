package runnershigh.capstone.crew.dto;

import lombok.Builder;

@Builder
public record CrewSearchCondition(
    String region,
    String name
) {

}
