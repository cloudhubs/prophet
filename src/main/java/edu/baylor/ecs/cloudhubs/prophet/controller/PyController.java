package edu.baylor.ecs.cloudhubs.prophet.controller;

import edu.baylor.ecs.cloudhubs.prophet.services.PyService;
import edu.baylor.ecs.cloudhubs.prophet.util.PyRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/py")
public class PyController {

    private final PyService service;

    public PyController(PyService service) {
        this.service = service;
    }

    @PostMapping("/project")
    public void processPythonProject(@RequestBody PyRequest request) {
        service.processPythonProject(request);
    }

    @PostMapping("/interface")
    public void matchServiceInterfaces(@RequestBody PyRequest request) {
        service.processInterfaceRequest(request);
    }
}
