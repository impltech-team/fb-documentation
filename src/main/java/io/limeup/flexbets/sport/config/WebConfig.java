package io.limeup.flexbets.sport.config;

import io.limeup.flexbets.sport.converter.CompetitionTypeConverter;
import io.limeup.flexbets.sport.converter.MarketTypeConverter;
import io.limeup.flexbets.sport.converter.SnakeCaseToCamelCaseArgumentResolver;
import io.limeup.flexbets.sport.converter.StatTargetTypeConverter;
import io.limeup.flexbets.sport.converter.StatusTypeConverter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final SnakeCaseToCamelCaseArgumentResolver argumentResolver;

    public WebConfig(SnakeCaseToCamelCaseArgumentResolver argumentResolver) {
        this.argumentResolver = argumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StatusTypeConverter());
        registry.addConverter(new StatTargetTypeConverter());
        registry.addConverter(new MarketTypeConverter());
        registry.addConverter(new CompetitionTypeConverter());
    }

    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        FilterRegistrationBean<ForwardedHeaderFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new ForwardedHeaderFilter());
        filter.setOrder(0);
        return filter;
    }
}
