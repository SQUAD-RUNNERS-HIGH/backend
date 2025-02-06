package runnershigh.capstone.jwt.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        log.info(accessToken);
        log.info(refreshToken);

        if (accessToken != null && jwtValidator.validateAccessToken(accessToken)) {
            if (refreshToken != null && jwtValidator.validateRefreshToken(
                refreshToken)) {                              // 엑세스 O, 리프레쉬 O -> 엑세스로 검증
                log.info("엑세스 토큰 O");
                processValidAccessToken(request, response, chain, accessToken, httpRequest);
                return;
            }
            UnauthorizedResponse(httpResponse);               // 엑세스 O, 리프레쉬 X -> 로그아웃 한 것
            return;
        }

        if (refreshToken != null && jwtValidator.validateRefreshToken(
            refreshToken)) {                                  // 엑세스 X, 리프레시 O -> 엑세스 토큰 만료
            log.info("엑세스 토큰 X");
            processValidRefreshToken(refreshToken, httpResponse, httpRequest);
            return;
        }
        UnauthorizedResponse(httpResponse);                   // 엑세스 X, 리프레쉬 X -> 로그인 안함
    }

    private void processValidAccessToken(ServletRequest request, ServletResponse response,
        FilterChain chain,
        String accessToken, HttpServletRequest httpRequest) throws IOException, ServletException {
        String userId = jwtExtractor.extractUserIdByAccessToken(accessToken);
        httpRequest.setAttribute("userId", userId);
        log.info(userId);

        chain.doFilter(request, response);
    }

    private void processValidRefreshToken(
        String refreshToken, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
        throws ServletException, IOException {

        String userId = jwtExtractor.extractUserIdByRefreshToken(refreshToken);
        String newAccessToken = jwtGenerator.generateAccessToken(userId);
        httpRequest.setAttribute("userId", userId);
        httpResponse.setHeader(AuthConstants.AUTHORIZATION_HEADER.getValue(),
            AuthConstants.BEARER_PREFIX.getValue() + newAccessToken);

        log.info("새로운 AccessToken 생성");
        log.info("{}{}", AuthConstants.BEARER_PREFIX.getValue(), newAccessToken);
        log.info("리프레쉬 토큰으로 새로운 엑세스 토큰 설정 완료!");

        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public String getHeader(String name) {
                if (AuthConstants.AUTHORIZATION_HEADER.getValue().equals(name)) {
                    return AuthConstants.BEARER_PREFIX.getValue() + newAccessToken;
                }
                return super.getHeader(name);
            }
        };
        httpRequest.getRequestDispatcher(httpRequest.getRequestURI())
            .forward(wrappedRequest, httpResponse);

    }

    private void UnauthorizedResponse(HttpServletResponse response)
        throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("AccessToken / RefreshToken is expired. Please login.");
    }

}
