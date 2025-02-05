package runnershigh.capstone.jwt.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
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

        if (path.equals("/auth/login")) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = jwtExtractor.extractAccessToken(httpRequest);
        String refreshToken = jwtExtractor.extractRefreshTokenFromCookie(httpRequest);

        if (accessToken != null && jwtValidator.validateAccessToken(accessToken)) {
            processValidAccessToken(request, response, chain, accessToken, httpRequest);
            return;
        }

        if (refreshToken != null && jwtValidator.validateRefreshToken(refreshToken)) {
            processValidRefreshToken(request, response, chain, refreshToken, httpResponse,
                httpRequest);
            return;
        }

        sendUnauthorizedResponse(httpResponse);
    }

    private void processValidAccessToken(ServletRequest request, ServletResponse response,
        FilterChain chain,
        String accessToken, HttpServletRequest httpRequest) throws IOException, ServletException {
        String userId = jwtExtractor.extractUserIdByAccessToken(accessToken);
        httpRequest.setAttribute("userId", userId);
        chain.doFilter(request, response);
    }

    private void processValidRefreshToken(ServletRequest request, ServletResponse response,
        FilterChain chain,
        String refreshToken, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
        throws IOException, ServletException {
        String userId = jwtExtractor.extractUserIdByRefreshToken(refreshToken);
        String newAccessToken = jwtGenerator.generateAccessToken(userId);

        httpResponse.setHeader(AuthConstants.AUTHORIZATION_HEADER.getValue(),
            AuthConstants.BEARER_PREFIX.getValue() + newAccessToken);

        log.info("새로운 AccessToken 생성");

        httpRequest.setAttribute("userId", userId);
        chain.doFilter(request, response);
    }

    private void sendUnauthorizedResponse(HttpServletResponse response)
        throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("AccessToken / RefreshToken is expired. Please login.");
    }

}
