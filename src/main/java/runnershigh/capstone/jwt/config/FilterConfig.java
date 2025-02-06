package runnershigh.capstone.jwt.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import runnershigh.capstone.jwt.filter.JwtFilter;
import runnershigh.capstone.jwt.service.JwtExtractor;
import runnershigh.capstone.jwt.service.JwtGenerator;
import runnershigh.capstone.jwt.service.JwtValidator;

@Configuration
@AllArgsConstructor
public class FilterConfig {

    private final JwtExtractor jwtExtractor;
    private final JwtValidator jwtValidator;
    private final JwtGenerator jwtGenerator;

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(jwtExtractor, jwtValidator, jwtGenerator));
        registrationBean.addUrlPatterns("/auth/*", "/user");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
