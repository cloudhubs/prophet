package edu.baylor.ecs.cloudhubs.prophet.application.controller;

import edu.baylor.ecs.cloudhubs.prophet.application.services.LoadDataService;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.LoadScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/load")
public class LoaderController {

    @Autowired
    private LoadDataService loadScriptService;

//    @Autowired
//    private LoadScriptService loadScriptService;

    @GetMapping(value = "/load")
    public void load(){
        loadScriptService.load("bounded-context.cql");
    }

}
