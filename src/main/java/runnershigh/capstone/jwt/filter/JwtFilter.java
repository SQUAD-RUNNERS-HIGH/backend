package runnershigh.capstone.jwt.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import runnershigh.capstone.jwt.enums.AuthConstants;
import runnershigh.capstone.jwt.service.JwtExtractor;
import runnershigh.capstone.jwt.service.JwtGenerator;
import runnershigh.capstone.jwt.service.JwtValidator;

@Slf4j
@AllArgsConstructor
public class JwtFilter implements Filter {

    private final JwtExtractor jwtExtractor;
    private final JwtValidator jwtValidator;
    private final JwtGenerator jwtGenerator;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        log.info("Request path: {}", path);

        if (path.equals("/auth/login") || path.equals("/user/register")) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = jwtExtractor.extractAccessToken(httpRequest);
        String refreshToken = jwtExtractor.extractRefreshTokenFromCookie(httpRequest);

        log.info("accessToken :{}", accessToken);
        log.info("refreshToken :{}", refreshToken);

        boolean isAccessValid = jwtValidator.validateAccessToken(accessToken);
        boolean isRefreshValid = jwtValidator.validateRefreshToken(refreshToken);

        if (path.equals("/auth/refresh")) {
            if (Objects.nonNull(accessToken) && isAccessValid) {
                log.info("만료되지 않았지만 accessToken 재발급");
                chain.doFilter(request, response);
            } else {
                UnauthorizedResponse(httpResponse);
            }
            return;
        }

        if (Objects.nonNull(accessToken) && isAccessValid) {
            if (Objects.nonNull(refreshToken) && isRefreshValid) {     // 엑세스 O, 리프레쉬 O -> 엑세스로 검증
                log.info("엑세스 토큰 O");
                chain.doFilter(request, response);
                return;
            }
            UnauthorizedResponse(httpResponse);               // 엑세스 O, 리프레쉬 X -> 로그아웃 한 것
            return;
        }

        if (Objects.nonNull(refreshToken) && isRefreshValid) {         // 엑세스 X, 리프레시 O -> 엑세스 토큰 만료
            log.info("엑세스 토큰 X");
            processValidRefreshToken(refreshToken, httpResponse);
            return;
        }
        UnauthorizedResponse(httpResponse);                   // 엑세스 X, 리프레쉬 X -> 로그인 안함
    }

    private void processValidRefreshToken(
        String refreshToken, HttpServletResponse httpResponse) {

        Long userId = jwtExtractor.extractUserIdByRefreshToken(refreshToken);
        String newAccessToken = jwtGenerator.generateAccessToken(userId);

        httpResponse.setHeader(AuthConstants.AUTHORIZATION_HEADER.getValue(),
            AuthConstants.BEARER_PREFIX.getValue() + newAccessToken);

        log.info("새로운 AccessToken : {}{}", AuthConstants.BEARER_PREFIX.getValue(), newAccessToken);
    }

    private void UnauthorizedResponse(HttpServletResponse response)
        throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("AccessToken / RefreshToken is expired. Please login.");
    }

}
