package edu.baylor.ecs.prophet.bounded.context.utils;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;

/**
 * Module's utility class to create bounded context from the system context
 */
public interface BoundedContextUtils {

    BoundedContext mergeEntities(SystemContext systemContext);

}
