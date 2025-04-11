package runnershigh.capstone.geocoding.service;

import org.springframework.stereotype.Component;
import runnershigh.capstone.geocoding.dto.GeocodingApiResponse;
import runnershigh.capstone.geocoding.exception.GeocodingNotFoundException;
import runnershigh.capstone.global.error.ErrorCode;

@Component
public class FormattedAddressExtractor {

    private static final int TARGET_INDEX = 6;

    public String extractFormattedAddress(GeocodingApiResponse response) {
        if (response.results().size() > TARGET_INDEX) {
            return response.results().get(TARGET_INDEX).formattedAddress();
        } else {
            throw new GeocodingNotFoundException(ErrorCode.GEOCODING_EXTRACT_FAILED);
        }
    }
}
