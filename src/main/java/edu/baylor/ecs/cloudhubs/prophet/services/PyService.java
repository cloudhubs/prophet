package edu.baylor.ecs.cloudhubs.prophet.services;

import edu.baylor.ecs.cloudhubs.prophet.util.PyRequest;
import edu.baylor.ecs.cloudhubs.prophetdto.mscontext.MsModel;
import edu.baylor.ecs.cloudhubs.prophetdto.pyparser.PySystem;
import edu.baylor.ecs.cloudhubs.prophetdto.pyparser.msapi.PyInterfaceResponse;
import edu.baylor.ecs.cloudhubs.prophetdto.pyparser.msapi.PyMsSystem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.pyparser.PySystem;

@Service
@Slf4j
public class PyService {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:5000/";
//    private PyParser parser; // initialize utils here

    public PyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void processPythonProject(PyRequest request) {
        PySystem system = parseSourceCode(request);
        System.out.println(system);
    }

    private PySystem parseSourceCode(PyRequest request) {
        String url = getParserURL("parse");
        HttpEntity<PyRequest> payload = new HttpEntity<>(request);
        return restTemplate.postForObject(url, payload, PySystem.class);
    }

    public MsModel processInterfaceRequest(PyRequest request) {
        String url = getParserURL("interface");
        HttpEntity<PyRequest> payload = new HttpEntity<>(request);
        PyMsSystem system = restTemplate.postForObject(url, payload, PyMsSystem.class);
//      return  parser.createMsModel(system);

        return null;
    }

    private String getParserURL(String endpoint) {
        return BASE_URL + endpoint;
    }
}
