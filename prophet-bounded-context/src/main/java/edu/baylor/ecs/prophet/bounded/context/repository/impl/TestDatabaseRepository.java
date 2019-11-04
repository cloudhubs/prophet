package edu.baylor.ecs.prophet.bounded.context.repository.impl;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;
import edu.baylor.ecs.prophet.bounded.context.repository.DatabseRepository;

import java.util.Map;

/**
 * Used to mimic a Database for testing purposes
 */
public class TestDatabaseRepository implements DatabseRepository {

    private Map<String, SystemContext> NameToSystem = null;

    @Override
    public SystemContext getAllEntityClassesInSystem(String systemName) {
        return this.NameToSystem.get(systemName);
    }

    public void addSystemContext(SystemContext sc){
        this.NameToSystem.put(sc.getSystemName(), sc);
    }

    /**
     * Nothing is actually perfomed on the Bounded Context
     * @param boundedContext the bounded context to persist
     * @return
     */
    @Override
    public BoundedContext createBoundedContext(BoundedContext boundedContext) {
        return boundedContext;
    }
}
