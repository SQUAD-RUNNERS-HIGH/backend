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

        if (accessToken != null && jwtProvider.validateAccessToken(accessToken)) {
            processValidAccessToken(request, response, chain, accessToken, httpRequest);
            return;
        }

        if (refreshToken != null && jwtProvider.validateRefreshToken(refreshToken)) {
            processValidRefreshToken(request, response, chain, refreshToken, httpResponse,
                httpRequest);
            return;
        }

        sendUnauthorizedResponse(httpResponse);


    }

    private void processValidAccessToken(ServletRequest request, ServletResponse response,
        FilterChain chain,
        String accessToken, HttpServletRequest httpRequest) throws IOException, ServletException {
        String loginId = jwtProvider.extractLoginIdByAccessToken(accessToken);
        httpRequest.setAttribute("loginId", loginId);
        chain.doFilter(request, response);
    }

    private void processValidRefreshToken(ServletRequest request, ServletResponse response,
        FilterChain chain,
        String refreshToken, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
        throws IOException, ServletException {
        String loginId = jwtProvider.extractLoginIdByRefreshToken(refreshToken);
        String newAccessToken = jwtProvider.generateAccessToken(loginId);

        httpResponse.setHeader(AuthConstants.AUTHORIZATION_HEADER.getValue(),
            AuthConstants.BEARER_PREFIX.getValue() + newAccessToken);

        log.info("새로운 AccessToken 생성");

        httpRequest.setAttribute("loginId", loginId);
        chain.doFilter(request, response);
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

    private void sendUnauthorizedResponse(HttpServletResponse response)
        throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("AccessToken / RefreshToken is expired. Please login.");
    }

}
