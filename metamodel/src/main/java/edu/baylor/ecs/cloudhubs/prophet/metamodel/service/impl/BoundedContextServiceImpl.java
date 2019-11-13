package edu.baylor.ecs.cloudhubs.prophet.metamodel.service.impl;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.service.BoundedContextService;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.service.FacadeService;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.SystemContext;
import edu.baylor.ecs.prophet.bounded.context.api.BoundedContextApi;
import edu.baylor.ecs.prophet.bounded.context.api.impl.BoundedContextApiImpl;
import org.springframework.stereotype.Service;

@Service
public class BoundedContextServiceImpl extends FacadeService implements BoundedContextService {

    public BoundedContext getBoundedContext(String systemName, Long forceUpdate) {
//        if (!hasSystemname(systemName)){
//            //
//            return Response;
//        }
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
        if (hasBoundedContext(systemName)){
            //if so get the bounded context from the database
            //ToDo: method
            getBoundedContextFromDb(systemName);
            return null;
        } else {
            //if not -> getBoundedContextFromLibrary
            return getBoundedContextFromLibrary(systemName);
        }

    }

    private void getBoundedContextFromDb(String systemName) {
    }

    /**
     * Checks if system is associated with bounded context object
     * @param systemName
     * @return
     */
    private boolean hasBoundedContext(String systemName) {

        return true;
    }

    private BoundedContext getBoundedContextFromLibrary(String systemName){
        //Get Context Maps for Bounded Context Library
        SystemContext systemContext = getAllEntityClassesInSystem(systemName);
        //Get Bounded Context from Context Maps (Bounded Context Library)
        BoundedContextApi boundedContextApi = new BoundedContextApiImpl();
        BoundedContext boundedContext = boundedContextApi.getBoundedContext(systemContext);
        //Persist to database
        boundedContext = persistBoundedContext(boundedContext);
        return boundedContext;
    }

    /**
     * Get all entity classes clustered by module
     * @param name
     * @return
     */
    private SystemContext getAllEntityClassesInSystem(String name){
        return null;
    }

    /**
     * Persist given bounded context to database
     * @param boundedContext
     * @return
     */
    private BoundedContext persistBoundedContext(BoundedContext boundedContext){
        //ToDo: persist context
        return boundedContext;
    }
}
