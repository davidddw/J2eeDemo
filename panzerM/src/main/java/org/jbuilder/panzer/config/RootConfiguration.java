package org.jbuilder.panzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;

@Configuration
@Import({ JettyConfiguration.class, DataConfiguration.class})
@ComponentScan(basePackages = { "org.jbuilder.panzer" },
        excludeFilters = { @ComponentScan.Filter(Controller.class),
        @ComponentScan.Filter(Configuration.class) })
public class RootConfiguration {
    /**
     * Allows access to properties. eg) @Value("${jetty.port}").
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}