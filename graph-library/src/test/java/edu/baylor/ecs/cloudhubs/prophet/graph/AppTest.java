package edu.baylor.ecs.cloudhubs.prophet.graph;


import edu.baylor.ecs.cloudhubs.prophet.graph.MyService;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbSystemService;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.EmbeddedDb;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest("service.message=Hello")
public class AppTest {
    @Autowired
    private MyService myService;

    @Autowired
    private EmbeddedDb embeddedDb;

    @Autowired
    private DbSystemService dbSystemService;

    @Test
    public void contextLoads() {
        Assertions.assertThat(myService.message()).isNotNull();
        Assertions.assertThat(embeddedDb.registerDb()).isNotNull();
    }

    @SpringBootApplication
    static class TestConfiguration {
    }

    @Test
    public void newTest(){
        System.out.println("new test");
        dbSystemService.create("SystemA");
        Optional<DbSystem> db = dbSystemService.testSystem();
        Assertions.assertThat(db.isPresent()).isTrue();

    }

}
