package edu.baylor.ecs.prophet.bounded.context.api.impl;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;
import edu.baylor.ecs.prophet.bounded.context.api.BoundedContextApi;
import edu.baylor.ecs.prophet.bounded.context.repository.DatabseRepository;
import edu.baylor.ecs.prophet.bounded.context.repository.impl.DatabseRepositoryImpl;
import edu.baylor.ecs.prophet.bounded.context.utils.BoundedContextUtils;
import edu.baylor.ecs.prophet.bounded.context.utils.impl.BoundedContextUtilsImpl;

public class BoundedContextApiImpl implements BoundedContextApi {

    @Override
    public BoundedContext getBoundedContext(String systemName) {
        DatabseRepository databseRepository = new DatabseRepositoryImpl();
        BoundedContextUtils boundedContextUtils = new BoundedContextUtilsImpl();

        // first get data from the DB
        SystemContext systemContext = databseRepository.getAllEntityClassesInSystem(systemName);

        // next merge the entities
        BoundedContext boundedContext = boundedContextUtils.createBoundedContext(systemContext);

        //now save the boundedContext to the DB
        return databseRepository.createBoundedContext(boundedContext);
    }
}
