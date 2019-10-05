package edu.baylor.ecs.prophet.bounded.context.repository.impl;

import edu.baylor.ecs.cloudhubs.database.DatabaseFacade;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;
import edu.baylor.ecs.prophet.bounded.context.repository.DatabseRepository;

public class DatabseRepositoryImpl implements DatabseRepository {
    @Override
    public SystemContext getAllEntityClassesInSystem(String systemName) {
        return DatabaseFacade.getAllEntityClassesInSystem(systemName);
    }

    @Override
    public BoundedContext createBoundedContext(BoundedContext boundedContext) {
        return DatabaseFacade.createBoundedContext(boundedContext);
    }
}
