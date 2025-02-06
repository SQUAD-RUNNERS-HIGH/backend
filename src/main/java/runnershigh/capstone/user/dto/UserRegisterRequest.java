package runnershigh.capstone.user.dto;

import runnershigh.capstone.user.domain.Physical;

public record UserRegisterRequest(
    String loginId,
    String password,
    String username,
    Physical physical
) {

}
