/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */
package edu.baylor.ecs.prophet.bounded.context.api;

import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.*;

/**
 * Interface to accept requests from outside
 * @author Ian Laird
 */
public interface BoundedContextApi {

    /**
     * 1. Calls database to get data - DatabaseRepository.getAllEntityClassesInSystem
     * 2. Applies merge function - BoundedContextUtils.merge
     * 3. Persist data - DatabaseRepository.createBoundedContext
     * @param systemName
     * @return
     */
    BoundedContext getBoundedContext(SystemContext systemName);
}
