/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.prophet.bounded.context.api.impl;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;
import edu.baylor.ecs.prophet.bounded.context.api.BoundedContextApi;
import edu.baylor.ecs.prophet.bounded.context.repository.DatabseRepository;
import edu.baylor.ecs.prophet.bounded.context.repository.impl.DatabseRepositoryImpl;
import edu.baylor.ecs.prophet.bounded.context.utils.BoundedContextUtils;
import edu.baylor.ecs.prophet.bounded.context.utils.impl.BoundedContextUtilsImpl;

/**
 * @author Ian Laird
 * @see edu.baylor.ecs.prophet.bounded.context.api.BoundedContextApi
 */
public class BoundedContextApiImpl implements BoundedContextApi {

    /**
     * gets {@link BoundedContext} for the given System name
     * @param systemName the name of the system
     * @return the bounded context for that system
     */
    @Override
    public BoundedContext getBoundedContext(String systemName) {

        // init the DB
        DatabseRepository databseRepository = new DatabseRepositoryImpl();

        // utility methods for Bounded Context
        BoundedContextUtils boundedContextUtils = new BoundedContextUtilsImpl();

        // retrieve the System Context from the DB
        SystemContext systemContext = databseRepository.getAllEntityClassesInSystem(systemName);

        // turn the system context into a bounded context
        BoundedContext boundedContext = boundedContextUtils.createBoundedContext(systemContext);

        //save the boundedContext to the DB
        return databseRepository.createBoundedContext(boundedContext);
    }
}
