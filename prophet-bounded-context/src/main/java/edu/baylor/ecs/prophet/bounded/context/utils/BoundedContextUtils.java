package edu.baylor.ecs.prophet.bounded.context.utils;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Entity;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Field;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Module;

import java.util.Map;
import java.util.Set;

/**
 * Module's utility class to create bounded context from the system context
 */
public interface BoundedContextUtils {

    BoundedContext createBoundedContext(SystemContext systemContext);

    Entity mergeEntities(Entity one, Entity two, Map<Field, Field> fieldMapping);

    Module mergeModules(Module one, Module two);

    Field mergeFields(Field one, Field two);

}
