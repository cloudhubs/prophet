package edu.baylor.ecs.cloudhubs.prophet.controller;

import edu.baylor.ecs.cloudhubs.prophet.services.JParserService;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JParserController {

    @Autowired
    private JParserService jParserService;

    public SystemContext parseApplication(String path) {
        return jParserService.getSystemContextFromFile(path);
    }

}
