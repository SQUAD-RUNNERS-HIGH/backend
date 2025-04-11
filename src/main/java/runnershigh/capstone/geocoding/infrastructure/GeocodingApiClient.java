package runnershigh.capstone.geocoding.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import runnershigh.capstone.geocoding.config.GeocodingProperties;
import runnershigh.capstone.geocoding.dto.GeocodingApiResponse;

@Component
public class GeocodingApiClient {

    private final RestClient restClient;
    private final GeocodingProperties geocodingProperties;

    public GeocodingApiClient(RestClient.Builder restClientBuilder,
        GeocodingProperties geocodingProperties) {
        this.restClient = restClientBuilder.baseUrl(geocodingProperties.getBaseUrl()).build();
        this.geocodingProperties = geocodingProperties;
    }

    public GeocodingApiResponse fetchAddress(double latitude, double longitude) {
        String API_REQUEST_PARAM = "?latlng=%.4f,%.4f&key=%s&language=ko";

        String url = String.format(API_REQUEST_PARAM, latitude, longitude,
            geocodingProperties.getKey());

        return restClient.get()
            .uri(url)
            .retrieve()
            .body(GeocodingApiResponse.class);
    }
}
