package edu.baylor.ecs.prophet.bounded.context.utils;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Entity;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Field;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;

import java.util.Map;
import java.util.Set;

/**
 * Module's utility class to create bounded context from the system context
 */
public interface BoundedContextUtils {

    BoundedContext mergeEntities(SystemContext systemContext);

    Entity merge(Entity one, Entity two, Map<Field, Field> fieldMapping);

    Field mergeFields(Field one, Field two);

}
