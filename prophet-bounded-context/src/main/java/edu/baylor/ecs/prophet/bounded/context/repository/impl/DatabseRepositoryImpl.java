/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.prophet.bounded.context.repository.impl;

 // import edu.baylor.ecs.cloudhubs.prophet.metamodel.service.BoundedContextDatabaseService;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.SystemContext;
import edu.baylor.ecs.prophet.bounded.context.repository.DatabseRepository;

/**
 * @author Ian Laird
 * @see DatabseRepository
 */
public class DatabseRepositoryImpl implements DatabseRepository {

    /**
     * gets {@link SystemContext} from a system name
     * @param systemName the name of the system
     * @return the system context
     */
    @Override
    public SystemContext getAllEntityClassesInSystem(String systemName) {
        return null;
         //return BoundedContextDatabaseService.getAllEntityClassesInSystem(systemName);
    }

    /**
     * saves {@link BoundedContext} to the DB
     * @param boundedContext the bounded context to persist
     * @return the saved bounded context
     */
    @Override
    public BoundedContext createBoundedContext(BoundedContext boundedContext) {
        return null;
        //return BoundedContextDatabaseService.createBoundedContext(boundedContext);
    }
}
