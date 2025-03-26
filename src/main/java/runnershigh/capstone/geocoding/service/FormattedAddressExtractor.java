package runnershigh.capstone.geocoding.service;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import runnershigh.capstone.geocoding.dto.GeocodingApiResponse;
import runnershigh.capstone.geocoding.exception.GeocodingNotFoundException;
import runnershigh.capstone.global.error.ErrorCode;

@Component
public class FormattedAddressExtractor {

    private static final int TARGET_INDEX = 6;

    public Mono<String> extractFormattedAddress(GeocodingApiResponse response) {
        if (response.results().size() > TARGET_INDEX) {
            return Mono.just(response.results().get(TARGET_INDEX).formattedAddress());
        } else {
            return Mono.error(() -> new GeocodingNotFoundException(
                ErrorCode.GEOCODING_EXTRACT_FAILED));
        }
    }
}
