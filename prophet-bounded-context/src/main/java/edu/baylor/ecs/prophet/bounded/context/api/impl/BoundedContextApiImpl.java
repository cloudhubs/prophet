/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.prophet.bounded.context.api.impl;

import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.SystemContext;
import edu.baylor.ecs.prophet.bounded.context.api.BoundedContextApi;
import edu.baylor.ecs.prophet.bounded.context.utils.BoundedContextUtils;
import edu.baylor.ecs.prophet.bounded.context.utils.impl.BoundedContextUtilsImpl;

/**
 * @author Ian Laird
 * @see edu.baylor.ecs.prophet.bounded.context.api.BoundedContextApi
 */
public class BoundedContextApiImpl implements BoundedContextApi {

    /**
     * gets {@link BoundedContext} for the given System name
     * @param systemContext context maps
     * @return the bounded context for that system
     */
    @Override
    public BoundedContext getBoundedContext(SystemContext systemContext) {
        BoundedContextUtils boundedContextUtils = new BoundedContextUtilsImpl();
        return boundedContextUtils.createBoundedContext(systemContext);
    }
}
