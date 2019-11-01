package edu.baylor.ecs.cloudhubs.prophet.application;

import edu.baylor.ecs.cloudhubs.prophet.graph.MyService;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.EmbeddedDb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "edu.baylor.ecs.cloudhubs.prophet")
@EnableNeo4jRepositories("edu.baylor.ecs.cloudhubs.prophet.graph.repository")
@RestController
public class App {

    private final MyService myService;

    private final EmbeddedDb embeddedDb;

    public App(MyService myService, EmbeddedDb embeddedDb) {
        this.myService = myService;
        this.embeddedDb = embeddedDb;
    }

    @GetMapping("/system")
    public String home() {
        embeddedDb.registerDb();
        return myService.message();
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}