package edu.baylor.ecs.prophet.bounded.context.api;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;

/**
 * Interface to accept requests from outside
 */
public interface BoundedContextApi {

    /**
     * 1. Calls database to get data - DatabaseRepository.getAllEntityClassesInSystem
     * 2. Applies merge function - BoundedContextUtils.merge
     * 3. Persist data - DatabaseRepository.createBoundedContext
     * @param systemName
     * @return
     */
    BoundedContext getBoundedContext(String systemName);
}
