package edu.baylor.ecs.cloudhubs.prophet;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("service")
@Configuration
public class ServiceProperties {

    /**
     * A message for the service.
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
