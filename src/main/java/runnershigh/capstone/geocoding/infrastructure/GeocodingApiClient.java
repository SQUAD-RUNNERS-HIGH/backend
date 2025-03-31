package runnershigh.capstone.geocoding.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import runnershigh.capstone.geocoding.config.GeocodingProperties;
import runnershigh.capstone.geocoding.dto.GeocodingApiResponse;

@Component
public class GeocodingApiClient {

    private final WebClient webClient;
    private final GeocodingProperties geocodingProperties;

    public GeocodingApiClient(WebClient.Builder webClientBuilder,
        GeocodingProperties geocodingProperties) {
        this.webClient = webClientBuilder.baseUrl(geocodingProperties.getBaseUrl()).build();
        this.geocodingProperties = geocodingProperties;
    }

    public Mono<GeocodingApiResponse> fetchAddress(double latitude, double longitude) {
        String API_REQUEST_PARAM = "?latlng=%f,%f&key=%s&language=ko";

        String url = String.format(API_REQUEST_PARAM, latitude, longitude,
            geocodingProperties.getKey());

        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(GeocodingApiResponse.class);
    }
}
