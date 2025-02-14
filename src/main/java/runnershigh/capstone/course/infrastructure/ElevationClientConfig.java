package runnershigh.capstone.course.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ElevationClientConfig {

    @Bean
    public ElevationClient elevationClient() {
        final RestClient restClient = RestClient.create();
        final RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        final HttpServiceProxyFactory factory =
            HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(ElevationClient.class);
    }
}
