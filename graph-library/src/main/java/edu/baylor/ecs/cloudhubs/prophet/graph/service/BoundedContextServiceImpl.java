package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.service.BoundedContextService;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoundedContextServiceImpl {

    @Autowired
    private DbSystemService dbSystemService;

    public BoundedContext getBoundedContext(String systemName, Long forceUpdate) {
        if (!hasSystemName(systemName)){
            // no system name is present
            return null;
        }
        if (forceUpdate.equals(new Long(1))){
            return forceUpdateStrategy(systemName);
        } else {
            return standardStrategy(systemName);
        }
    }

    private boolean hasSystemName(String systemName) {
        DbSystem dbSystem = dbSystemService.getSystem(systemName);
        if (dbSystem != null){
            return true;
        } else {
            return false;
        }
    }

    public BoundedContext forceUpdateStrategy(String systemName){
        return getBoundedContextFromLibrary(systemName);
    }

    /**
     * Standard strategy does not update bounded context if it already exists in the database
     * @param systemName
     * @return
     */
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
//        BoundedContextApi boundedContextApi = new BoundedContextApiImpl();
//        BoundedContext boundedContext = boundedContextApi.getBoundedContext(systemContext);
//        //Persist to database
//        boundedContext = persistBoundedContext(boundedContext);
        return null;
    }

    /**
     * Get all entity classes clustered by module
     * @param name
     * @return
     */
    private SystemContext getAllEntityClassesInSystem(String name){
        //get modules for system
        //for each all classes that has rel to module and entity
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
