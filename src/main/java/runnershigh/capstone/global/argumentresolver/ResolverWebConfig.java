package runnershigh.capstone.global.argumentresolver;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import runnershigh.capstone.jwt.service.JwtExtractor;

@Configuration
@RequiredArgsConstructor
public class ResolverWebConfig implements WebMvcConfigurer {

    private final JwtExtractor jwtExtractor;

    @Bean
    public AuthUserArgumentResolver userArgumentResolver() {
        return new AuthUserArgumentResolver(jwtExtractor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver());
    }
}
