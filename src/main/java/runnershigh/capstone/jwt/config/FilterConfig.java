package runnershigh.capstone.jwt.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import runnershigh.capstone.jwt.filter.JwtFilter;
import runnershigh.capstone.jwt.util.JwtProvider;

@Configuration
@AllArgsConstructor
public class FilterConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(jwtProvider));
        registrationBean.addUrlPatterns("/auth/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
