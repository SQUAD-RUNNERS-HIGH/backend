package runnershigh.capstone.geocoding.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GeocodingProperties {

    @Value("${geocoding.key}")
    private String key;
}
