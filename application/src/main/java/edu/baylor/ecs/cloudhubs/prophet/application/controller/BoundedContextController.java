package edu.baylor.ecs.cloudhubs.prophet.application.controller;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.service.impl.BoundedContextServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/bounded")
public class BoundedContextController {

    @Autowired
    private BoundedContextServiceImpl boundedContextServiceImpl;


    @GetMapping(value = "/{systemName}/{forceUpdate}")
    public BoundedContext getBoundedContextBySystemName(@PathVariable String systemName,
                                                        @PathVariable Long forceUpdate){
        return boundedContextServiceImpl.getBoundedContext(systemName, forceUpdate);
    }
}
