package edu.baylor.ecs.cloudhubs.prophet.metamodel.service;


import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.BoundedContext;

public interface BoundedContextService {

    /**
     * Get Bounded Context of a system
     * @param systemName
     * @return
     */
    BoundedContext getBoundedContext(String systemName, Long forceUpdate);
}
