package runnershigh.capstone.geocoding.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import runnershigh.capstone.geocoding.dto.FormattedAddressResponse;
import runnershigh.capstone.geocoding.exception.GeocodingNotFoundException;
import runnershigh.capstone.geocoding.infrastructure.GeocodingApiClient;
import runnershigh.capstone.global.error.ErrorCode;

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

    public Mono<FormattedAddressResponse> getFormattedAddress(double latitude, double longitude) {
        return geocodingApiClient.fetchAddress(latitude, longitude)
            .flatMap(addressExtractor::extractFormattedAddress)
            .map(this::parseAddress)
            .switchIfEmpty(
                Mono.error(() -> new GeocodingNotFoundException(ErrorCode.GEOCODING_NOT_FOUND)));
    }

    private FormattedAddressResponse parseAddress(String formattedAddress) {
        String[] parts = formattedAddress.split(SPLIT_STRING);

        if (parts.length < TARGET_INDEX) {
            throw new GeocodingNotFoundException(ErrorCode.INCORRECT_GEOCODING_FORMAT);
        }

        return new FormattedAddressResponse(parts[0], parts[1], parts[2], parts[3]);
    }


}