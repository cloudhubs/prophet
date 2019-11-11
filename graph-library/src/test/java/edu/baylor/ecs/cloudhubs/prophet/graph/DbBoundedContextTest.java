package edu.baylor.ecs.cloudhubs.prophet.graph;

import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbBoundedContextService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
@Configurable
@Configuration
@ComponentScan({"edu.baylor.ecs.cloudhubs.prophet.graph.service", "edu.baylor.ecs.cloudhubs.prophet.metamodel" +
        ".service"})
public class DbBoundedContextTest {

    @Autowired
    private DbBoundedContextService dbBoundedContextService;

    @SpringBootApplication
    static class TestConfiguration {
    }


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void cleanUp() throws Exception {

    }


    @Test()
    public void getBounded(){
//        dbBoundedContextService.load();
    }



}
