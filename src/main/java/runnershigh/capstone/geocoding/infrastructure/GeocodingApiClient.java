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
        String API_REQUEST_PARAM =
            "?service=address&request=getAddress&version=2.0&crs=epsg:4326" +
                "&point=%.6f,%.6f&format=json&type=parcel&zipcode=true&simple=false&key=%s";

        // 위도, 경도의 순서를 API 문서에 따라 '경도,위도'로 바꿈
        String url = String.format(API_REQUEST_PARAM, longitude, latitude,
            geocodingProperties.getKey());

        return restClient.get()
            .uri(url)
            .retrieve()
            .body(GeocodingApiResponse.class);
    }
}
