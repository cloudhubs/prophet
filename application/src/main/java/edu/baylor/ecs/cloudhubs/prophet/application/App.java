package edu.baylor.ecs.cloudhubs.prophet.application;

import edu.baylor.ecs.cloudhubs.prophet.graph.MyService;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbSystemService;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.EmbeddedDb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication(scanBasePackages = "edu.baylor.ecs.cloudhubs.prophet")
@EnableNeo4jRepositories("edu.baylor.ecs.cloudhubs.prophet.graph.repository")
@RestController
@EntityScan("edu.baylor.ecs.cloudhubs.prophet.model")
public class App {

    private final MyService myService;

    private final EmbeddedDb embeddedDb;

    private final DbSystemService dbSystemService;

    public App(MyService myService, EmbeddedDb embeddedDb, DbSystemService dbSystemService) {
        this.myService = myService;
        this.embeddedDb = embeddedDb;
        this.dbSystemService = dbSystemService;
    }

    @GetMapping("/system")
    public String home() {
        embeddedDb.registerDb();
        return myService.message();
    }

    @GetMapping("/all")
    public Iterable<DbSystem> getAll(){
        embeddedDb.registerDb();
        dbSystemService.createByName("My Service X");
        Iterable<DbSystem> it = dbSystemService.getAll();
        embeddedDb.shutDown();
        return it;

    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}