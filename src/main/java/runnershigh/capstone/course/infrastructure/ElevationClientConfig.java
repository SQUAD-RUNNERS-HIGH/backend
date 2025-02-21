package runnershigh.capstone.course.infrastructure;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@Slf4j
public class ElevationClientConfig {

    @Value("${runnershigh.elevation.url}")
    private String elevationApiUrl;

    @Bean
    public ElevationClient elevationClient() {
        final RestClient restClient = RestClient.builder()
            .baseUrl(elevationApiUrl)
            .requestInterceptor((request, body,execution) -> {
                return execution.execute(request,body);
            })
            .build();
        final RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        final HttpServiceProxyFactory factory =
            HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(ElevationClient.class);
    }
}
