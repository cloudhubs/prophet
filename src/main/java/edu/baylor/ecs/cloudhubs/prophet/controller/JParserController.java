package edu.baylor.ecs.cloudhubs.prophet.controller;

import edu.baylor.ecs.cloudhubs.prophet.services.JParserService;
import edu.baylor.ecs.cloudhubs.prophetdto.app.ProphetRequest;
import edu.baylor.ecs.cloudhubs.prophetdto.app.ProphetResponse;
import edu.baylor.ecs.cloudhubs.prophetutils.ProphetUtilsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class JParserController {

    @Autowired
    private JParserService jParserService;

//    @PostMapping("/")
//    public SystemContext parseApplication(@RequestBody ProphetRequest request) {
//        return jParserService.getSystemContextFromFile(request.getUrl());
//    }

    @PostMapping("/analyze")
    public ProphetResponse parseApplication(@RequestBody ProphetRequest pr) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        ProphetRequest pr = mapper.readValue(req.toString(), ProphetRequest.class);
        return ProphetUtilsFacade.getProphetResponse(pr.getUrl());
    }

}
