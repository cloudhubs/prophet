package edu.baylor.ecs.cloudhubs.prophet.graph;


import edu.baylor.ecs.cloudhubs.prophet.graph.MyService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest("service.message=Hello")
public class AppTest {
    @Autowired
    private MyService myService;

    @Test
    public void contextLoads() {
        Assertions.assertThat(myService.message()).isNotNull();
    }

    @SpringBootApplication
    static class TestConfiguration {
    }

    @Test
    public void newTest(){
        System.out.println("new test");
    }

}
