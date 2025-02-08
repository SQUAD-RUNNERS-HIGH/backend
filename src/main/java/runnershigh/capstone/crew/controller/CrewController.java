package runnershigh.capstone.crew.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.crew.dto.CrewCreateRequest;
import runnershigh.capstone.crew.dto.CrewCreateResponse;
import runnershigh.capstone.crew.dto.CrewSearchResponse;
import runnershigh.capstone.crew.service.CrewService;
import runnershigh.capstone.jwt.service.JwtExtractor;

@RestController
@RequestMapping("/crew")
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;
    private final JwtExtractor jwtExtractor;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @PostMapping
    public CrewCreateResponse createCrew(HttpServletRequest request,
        @RequestBody CrewCreateRequest crewCreateRequest) {
        String crewLeaderId = extractUserIdFromToken(request);
        return crewService.createCrew(crewLeaderId, crewCreateRequest);
    }

    @GetMapping("/surround")
    public CrewSearchResponse searchCrew(HttpServletRequest request) {
        String userId = extractUserIdFromToken(request);
        return crewService.searchCrew(userId);
    }

    private String extractUserIdFromToken(HttpServletRequest request) {
        String accessToken = request.getHeader(AUTHORIZATION_HEADER);
        String refineToken = accessToken.replace(BEARER_PREFIX, "").trim();
        return jwtExtractor.extractUserIdByAccessToken(refineToken);
    }
}
