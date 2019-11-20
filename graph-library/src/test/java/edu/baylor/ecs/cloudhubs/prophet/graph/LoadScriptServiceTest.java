package edu.baylor.ecs.cloudhubs.prophet.graph;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbSystemService;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.LoadScriptService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class LoadScriptServiceTest {

    @Autowired
    private LoadScriptService loadScriptService;

    @Autowired
    private DbSystemService dbSystemService;

    @Value("${script.test}")
    private String testScript;

    @Test()
    public void loadTestScript(){
        this.loadScriptService.load(testScript);
        DbSystem dbSystem = this.dbSystemService.getSystem("TestSystem");
        Assertions.assertThat(dbSystem.getName()).isEqualTo("TestSystem");
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}
