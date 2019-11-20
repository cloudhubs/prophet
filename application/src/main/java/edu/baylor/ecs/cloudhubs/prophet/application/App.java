package edu.baylor.ecs.cloudhubs.prophet.application;

import edu.baylor.ecs.cloudhubs.prophet.application.services.LoadDataService;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.web.client.RestTemplate;

@EntityScan({"edu.baylor.ecs.cloudhubs.prophet.metamodel.db","edu.baylor.ecs.cloudhubs.prophet.graph.model"})
@SpringBootApplication(scanBasePackages = "edu.baylor.ecs.cloudhubs.prophet")
@EnableNeo4jRepositories(
        {"edu.baylor.ecs.cloudhubs.prophet.metamodel.repository",
         "edu.baylor.ecs.cloudhubs.prophet.graph.repository"})
@ComponentScan(
        {   "edu.baylor.ecs.cloudhubs.prophet.metamodel.service",
             "edu.baylor.ecs.cloudhubs.prophet.graph.service",
                "edu.baylor.ecs.cloudhubs.prophet.application.services",
                "edu.baylor.ecs.cloudhubs.prophet.application.controller",
        })
public class App {

    private final static Logger log = LoggerFactory.getLogger(App.class);


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner demo(LoadDataService loadDataService, DbSystemService dbSystemService) {
        return args -> {
            loadDataService.load("bounded-context.cql");
            dbSystemService.createByName("Test");
            Iterable<DbSystem> dbs =  dbSystemService.getAll();
            dbs.forEach(n -> {
                System.out.println(n.getName());
            });

        };
    }


//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }

}