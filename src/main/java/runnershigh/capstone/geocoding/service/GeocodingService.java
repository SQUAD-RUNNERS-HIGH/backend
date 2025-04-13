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

    private static final int TARGET_INDEX = 4;
    private static final String SPLIT_STRING = " ";

    private final GeocodingApiClient geocodingApiClient;
    private final FormattedAddressExtractor addressExtractor;

    public GeocodingService(GeocodingApiClient geocodingApiClient,
        FormattedAddressExtractor addressExtractor) {
        this.geocodingApiClient = geocodingApiClient;
        this.addressExtractor = addressExtractor;
    }

    public FormattedAddressResponse getFormattedAddress(double latitude, double longitude) {
        GeocodingApiResponse response = geocodingApiClient.fetchAddress(latitude, longitude);

        if (Objects.isNull(response)) {
            throw new GeocodingNotFoundException(ErrorCode.GEOCODING_NOT_FOUND);
        }

        String formattedAddress = addressExtractor.extractFormattedAddress(response);
        return parseAddress(formattedAddress);
    }

    private FormattedAddressResponse parseAddress(String formattedAddress) {
        String[] parts = formattedAddress.split(SPLIT_STRING);

        String part1 = parts.length > 0 ? parts[0] : "";
        String part2 = parts.length > 1 ? parts[1] : "";
        String part3 = parts.length > 2 ? parts[2] : "";
        String part4 = parts.length > 3 ? parts[3] : "";

        return new FormattedAddressResponse(part1, part2, part3, part4);
    }
}
