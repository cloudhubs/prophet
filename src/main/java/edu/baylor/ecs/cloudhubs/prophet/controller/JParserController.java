package edu.baylor.ecs.cloudhubs.prophet.controller;

import edu.baylor.ecs.cloudhubs.prophet.model.ProphetRequest;
import edu.baylor.ecs.cloudhubs.prophet.services.JParserService;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JParserController {

    @Autowired
    private JParserService jParserService;

    @PostMapping("/")
    public SystemContext parseApplication(@RequestBody ProphetRequest request) {
        return jParserService.getSystemContextFromFile(request.getUrl());
    }

}
