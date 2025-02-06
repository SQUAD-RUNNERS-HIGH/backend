package runnershigh.capstone.jwt.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import runnershigh.capstone.jwt.enums.AuthConstants;

@Component
public class CookieUtil {

    private static final String COOKIE_HEADER = "Set-Cookie";

    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken,
        Long expirationTime) {
        ResponseCookie cookie = ResponseCookie.from(AuthConstants.REFRESH_COOKIE_NAME.getValue(),
                refreshToken)
            .maxAge(expirationTime)
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build();
        response.setHeader(COOKIE_HEADER, cookie.toString());
    }

    public void clearRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie expiredCookie = ResponseCookie.from(
                AuthConstants.REFRESH_COOKIE_NAME.getValue(), "")
            .maxAge(0)  // 즉시 삭제
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build();
        response.setHeader(COOKIE_HEADER, expiredCookie.toString());
    }
}
