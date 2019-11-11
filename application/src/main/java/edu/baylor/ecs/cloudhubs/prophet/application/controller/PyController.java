package edu.baylor.ecs.cloudhubs.prophet.application.controller;

import edu.baylor.ecs.cloudhubs.prophet.application.services.PyService;
import edu.baylor.ecs.cloudhubs.prophet.application.util.PyRequest;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbSystem;
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

    @PostMapping
    public DbSystem processPythonProject(@RequestBody PyRequest request) {
        // call py-service to process request
        return service.processPythonProject(request);
    }
}
