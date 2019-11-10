package edu.baylor.ecs.cloudhubs.prophet.application.services;

import edu.baylor.ecs.cloudhubs.prophet.application.util.PyRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PyServiceTest {

    @Autowired
    private PyService service;

    @Test
    public void processPythonProject() {
        PyRequest request = new PyRequest();
        request.setFileName("/Users/yeboah/Python/py-testbd/app");
        request.setGitIgnore(true);

        service.processPythonProject(request);
    }
}