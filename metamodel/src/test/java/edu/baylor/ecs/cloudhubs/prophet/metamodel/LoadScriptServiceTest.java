package edu.baylor.ecs.cloudhubs.prophet.metamodel;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.service.LoadScriptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoadScriptService.class)
@Transactional
public class LoadScriptServiceTest {

    @Autowired
    private LoadScriptService loadScriptService;

    @Autowired
    private Session session;



    @Test
    public void shouldAnswerWithTrue()
    {
        loadScriptService.load("path");
        assertTrue( true );
    }

    @SpringBootApplication
    static class TestConfiguration {
    }

}
