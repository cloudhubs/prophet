package edu.baylor.ecs.cloudhubs.prophet.bytecode;


import edu.baylor.ecs.cloudhubs.prophet.bytecode.service.ResourceService;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResourceServiceTest
{

    @Autowired
    private ResourceService resourceService;

//    @SpringBootApplication
//    static class TestConfiguration {
//    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        List<String> l = resourceService.getResourcePaths("/Users/svacina/dev/cil-experiments/01bounded-context-cm5" +
                "/cm5");
        Assertions.assertThat( l.size() ).isEqualTo(2002);
    }
}
