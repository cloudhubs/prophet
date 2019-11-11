package edu.baylor.ecs.cloudhubs.prophet.application.services;

import edu.baylor.ecs.cloudhubs.prophet.application.exception.RestErrorHandler;
import edu.baylor.ecs.cloudhubs.prophet.application.util.PyRequest;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.PySystem;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.service.PyMetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PyService {

    private final RestTemplate restTemplate;
    private final PyMetaService pyMetaService;

    public PyService(RestTemplate restTemplate, PyMetaService pyMetaService) {
        this.restTemplate = restTemplate;
        this.pyMetaService = pyMetaService;
    }

    public DbSystem processPythonProject(PyRequest request) {
        // create a request to py-parser api
        PySystem system = parseSourceCode(request);

        // use target to process response
        return pyMetaService.persistPyData(system);
    }

    private PySystem parseSourceCode(PyRequest request) {
        restTemplate.setErrorHandler(new RestErrorHandler());

        String url = "http://localhost:5000/parse";
        HttpEntity<PyRequest> payload = new HttpEntity<>(request);
        return restTemplate.postForObject(url, payload, PySystem.class);
    }
}
