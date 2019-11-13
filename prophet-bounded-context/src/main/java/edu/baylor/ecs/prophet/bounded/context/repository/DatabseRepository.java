/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.prophet.bounded.context.repository;

import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.SystemContext;

/**
 * Interface to communicate with neo4j database
 */
public interface DatabseRepository {

    /**
     * get all context models from the database based on system name
     * @param systemName the name of the system
     * @return the system context
     */
    SystemContext getAllEntityClassesInSystem(String systemName);

    /**
     * persist data to the DB
     * @param boundedContext the bounded context to persist
     * @return the bounded context that was persisted
     */
    BoundedContext createBoundedContext(BoundedContext boundedContext);

}
