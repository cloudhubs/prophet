package edu.baylor.ecs.cloudhubs.prophet.controller;

import edu.baylor.ecs.cloudhubs.prophet.services.DbSystemService;
import edu.baylor.ecs.cloudhubs.prophet.services.JParserService;
import edu.baylor.ecs.cloudhubs.prophet.services.ProphetDataService;
import edu.baylor.ecs.cloudhubs.prophetdto.app.ProphetAppData;
import edu.baylor.ecs.cloudhubs.prophetdto.app.ProphetAppRequest;
import edu.baylor.ecs.cloudhubs.prophetdto.app.ProphetRequest;
import edu.baylor.ecs.cloudhubs.prophetdto.app.ProphetResponse;
import edu.baylor.ecs.cloudhubs.prophetdto.communication.Communication;
import edu.baylor.ecs.cloudhubs.prophetdto.communication.ContextMap;
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

    @Autowired
    private ProphetDataService prophetDataService;

//    @PostMapping("/")
//    public SystemContext parseApplication(@RequestBody ProphetRequest request) {
//        return jParserService.getSycstemContextFromFile(request.getUrl());
//    }

    @PostMapping("/communication")
    public Communication getCommunication(@RequestBody ProphetRequest pr) throws IOException {
        String localPath = pr.getUrl();
        return ProphetUtilsFacade.getCommunication(pr.getUrl());
    }

    @PostMapping("/contextmap")
    public ContextMap getContextMap(@RequestBody ProphetRequest pr) throws IOException {
        String localPath = pr.getUrl();
        return ProphetUtilsFacade.getContextMap(pr.getUrl());
    }

    @PostMapping("/app")
    public ProphetAppData getAppData(@RequestBody ProphetAppRequest request) throws IOException {
        ProphetAppData response = ProphetUtilsFacade.getProphetAppData(request.getPath());
        if (request.isPersistDb()) {
            // TODO: persist to database
            //prophetDataService.persistProphetData(response);
        }
        return response;
    }

}
