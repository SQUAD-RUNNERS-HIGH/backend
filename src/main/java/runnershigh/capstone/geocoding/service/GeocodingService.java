package runnershigh.capstone.geocoding.service;

import java.util.Arrays;
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

        if (parts.length < TARGET_INDEX) {
            log.warn("적절하지 않게 출력된 포맷 : {}", Arrays.toString(parts));
            throw new GeocodingNotFoundException(ErrorCode.INCORRECT_GEOCODING_FORMAT);
        }

        return new FormattedAddressResponse(parts[0], parts[1], parts[2], parts[3]);
    }
}
