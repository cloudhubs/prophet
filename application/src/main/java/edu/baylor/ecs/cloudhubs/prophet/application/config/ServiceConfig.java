package edu.baylor.ecs.cloudhubs.prophet.application.config;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.service.PyMetaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public PyMetaService getPyMetaService() {
        return new PyMetaService();
    }
}
