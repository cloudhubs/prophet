package edu.baylor.ecs.cloudhubs.prophet.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.pyparser.PySystem;

@Service
@Slf4j
public class PyService {

//    private final RestTemplate restTemplate;
//
//    public PyService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public void processPythonProject(PyRequest request) {
//        // create a request to py-parser api
//        PySystem system = parseSourceCode(request);
//        System.out.println(system);
//        // use target to process response
//    }
//
//    private PySystem parseSourceCode(PyRequest request) {
//        restTemplate.setErrorHandler(new RestErrorHandler());
//
//        String url = "http://localhost:5000/parse";
//        HttpEntity<PyRequest> payload = new HttpEntity<>(request);
//        return restTemplate.postForObject(url, payload, PySystem.class);
//    }
}
