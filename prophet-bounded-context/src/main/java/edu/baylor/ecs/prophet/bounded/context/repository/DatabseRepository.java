package edu.baylor.ecs.prophet.bounded.context.repository;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;

/**
 * Interface to communicate with neo4j database
 */
public interface DatabseRepository {

    // get all context models from the database based on system name
    SystemContext getAllEntityClassesInSystem(String systemName);

    // persist data to database
    BoundedContext createBoundedContext(BoundedContext boundedContext);

}
