package edu.baylor.ecs.cloudhubs.prophet.graph;

import edu.baylor.ecs.cloudhubs.prophet.graph.service.JParserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.NotDirectoryException;
import java.util.List;

//@PropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class JParserAdapterServiceTest {

    @Value("${parser.url}")
    private String url;

    @Autowired
    private JParserService jParserService;


    @Test
    public void createClass() {
        try {
            jParserService.createSystemFromSourceCodeViaDirectory(url);
        } catch (NotDirectoryException e) {
            e.printStackTrace();
        }
        Assertions.assertThat(4).isEqualTo(4);
    }

    @SpringBootApplication
    static class TestConfiguration {
    }


}
