package edu.baylor.ecs.cloudhubs.prophet;

import edu.baylor.ecs.cloudhubs.prophet.services.LoadDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories("edu.baylor.ecs.cloudhubs.prophet.repositories")
public class ProphetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProphetApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(LoadDataService loadDataService) {
        return args -> {
            loadDataService.load("bounded-context.cql");
        };
    }
}