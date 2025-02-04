package runnershigh.capstone.jwt.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import runnershigh.capstone.jwt.enums.AuthConstants;
import runnershigh.capstone.jwt.util.JwtProvider;

@Slf4j
@AllArgsConstructor
public class JwtFilter implements Filter {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        if (path.equals("/auth/login")) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = extractToken(httpRequest);
        String refreshToken = extractRefreshTokenFromCookie(httpRequest);

        if (accessToken != null) { // 로그인 자체가 되지 않은 경우
            if (jwtProvider.validateAccessToken(accessToken)) {        // accessToken 검증 성공
                String loginId = jwtProvider.extractLoginIdByAccessToken(accessToken);
                httpRequest.setAttribute("loginId", loginId);
                chain.doFilter(request, response);
            } else {                                                   // accessToken 검증 실패
                if (refreshToken != null && jwtProvider.validateRefreshToken(
                    refreshToken)) {                                   // refreshToken 검증 성공
                    String loginId = jwtProvider.extractLoginIdByRefreshToken(refreshToken);
                    String newAccessToken = jwtProvider.generateAccessToken(loginId);

                    httpResponse.setHeader(AuthConstants.AUTHORIZATION_HEADER.getValue(),
                        AuthConstants.BEARER_PREFIX.getValue() + newAccessToken);

                    log.info("새로운 AccessToken 생성");

                    httpRequest.setAttribute("loginId", loginId);
                    chain.doFilter(request, response);
                } else {                                              // refreshToken 검증 실패
                    sendUnauthorizedResponse(httpResponse,
                        "AccessToken / RefreshToken is expired. plz login");
                }
            }
        } else {
            sendUnauthorizedResponse(httpResponse, "AccessToken is expired. plz login");
        }
    }

    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader(AuthConstants.AUTHORIZATION_HEADER.getValue());
        if (token != null && token.startsWith(AuthConstants.BEARER_PREFIX.getValue())) {
            return token.substring(7);  // "Bearer " 부분을 잘라내고 실제 토큰만 반환
        }
        return null;
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AuthConstants.REFRESH_COOKIE_NAME.getValue().equals(cookie.getName())) {
                    return cookie.getValue();  // 쿠키에서 리프레시 토큰 반환
                }
            }
        }
        return null;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message)
        throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }

}
