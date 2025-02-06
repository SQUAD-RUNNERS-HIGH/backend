package runnershigh.capstone.user.dto;


import runnershigh.capstone.user.domain.Physical;

public record UserResponse(
    String loginId,
    String username,
    Physical physical
) {

}
