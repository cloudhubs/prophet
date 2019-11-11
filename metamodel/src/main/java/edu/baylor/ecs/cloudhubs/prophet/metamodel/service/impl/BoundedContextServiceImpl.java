package edu.baylor.ecs.cloudhubs.prophet.metamodel.service.impl;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.service.BoundedContextService;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.service.FacadeService;
import org.springframework.stereotype.Service;

@Service
public class BoundedContextServiceImpl extends FacadeService implements BoundedContextService {

    public BoundedContext getBoundedContext(String systemName, Long forceUpdate) {
        if (forceUpdate.equals(new Long(1))){
            return forceUpdateStrategy(systemName);
        } else {
            return standardStrategy(systemName);
        }
    }

    public BoundedContext forceUpdateStrategy(String systemName){
        return getBoundedContextFromLibrary(systemName);
    }

    public BoundedContext standardStrategy(String systemName){
        //Check if boundedContext in the database

        //if so get the bounded context from the database

        //if not -> getBoundedContextFromLibrary

        return null;

    }

    public BoundedContext getBoundedContextFromLibrary(String systemName){
        //Get Context Maps for Bounded Context Library
        SystemContext systemContext = getAllEntityClassesInSystem(systemName);
        //Get Bounded Context from Context Maps
        BoundedContext boundedContext = null; // = boundedContextUtils.createBoundedContext(systemContext);
        //Persist to database
        boundedContext = createBoundedContext(boundedContext);
        return boundedContext;
    }

    private SystemContext getAllEntityClassesInSystem(String name){
        return null;
    }

    private BoundedContext createBoundedContext(BoundedContext boundedContext){
        //ToDo: persist context
        return boundedContext;
    }
}
