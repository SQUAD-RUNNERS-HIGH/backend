package runnershigh.capstone.geocoding.service;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import runnershigh.capstone.geocoding.dto.FormattedAddressResponse;
import runnershigh.capstone.geocoding.dto.GeocodingApiResponse;
import runnershigh.capstone.geocoding.exception.GeocodingNotFoundException;
import runnershigh.capstone.geocoding.infrastructure.GeocodingApiClient;
import runnershigh.capstone.global.error.ErrorCode;

@Slf4j
@Service
public class GeocodingService {

    private final GeocodingApiClient geocodingApiClient;

    public GeocodingService(GeocodingApiClient geocodingApiClient) {
        this.geocodingApiClient = geocodingApiClient;
    }

    public FormattedAddressResponse getFormattedAddress(double latitude, double longitude) {
        GeocodingApiResponse response = geocodingApiClient.fetchAddress(latitude, longitude);

        if (Objects.isNull(response)) {
            throw new GeocodingNotFoundException(ErrorCode.GEOCODING_NOT_FOUND);
        }
        return response.toFormattedAddressResponse();
    }

    public GeocodingApiResponse getGeocodingApiResponse(double latitude, double longitude) {
        return geocodingApiClient.fetchAddress(latitude, longitude);
    }
}
