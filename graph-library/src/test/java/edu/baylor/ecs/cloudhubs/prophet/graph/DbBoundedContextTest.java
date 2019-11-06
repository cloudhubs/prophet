package edu.baylor.ecs.cloudhubs.prophet.graph;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class DbBoundedContextTest {

    @SpringBootApplication
    static class TestConfiguration {
    }


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void cleanUp() throws Exception {

    }



}
