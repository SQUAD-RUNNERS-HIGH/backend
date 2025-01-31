package runnershigh.capstone.user.dto;

import runnershigh.capstone.user.domain.Physical;

public record UserProfileRequest(
    String password,
    String username,
    Physical physical
) {

}
