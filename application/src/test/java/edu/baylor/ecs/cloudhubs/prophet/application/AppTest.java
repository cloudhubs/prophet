package edu.baylor.ecs.cloudhubs.prophet.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import edu.baylor.ecs.cloudhubs.prophet.graph.MyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Autowired
    private MyService myService;

    @Test
    public void contextLoads() {
        assertThat(myService).isNotNull();
    }

}