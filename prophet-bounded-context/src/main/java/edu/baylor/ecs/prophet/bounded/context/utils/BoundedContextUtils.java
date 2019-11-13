/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.prophet.bounded.context.utils;


import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.*;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.Module;

import java.util.Map;

/**
 * Module's utility class to create bounded context from the system context
 */
public interface BoundedContextUtils {

    /**
     * creates bounded context from a system context
     * @param systemContext the system context
     * @return the bounded context
     */
    BoundedContext createBoundedContext(SystemContext systemContext);

    /**
     * merges two entities together using the field mapping
     * @param one the first entity to merge
     * @param two the second entity to merge
     * @param fieldMapping the mapping between the fields of the entities
     * @return the newly creataed merged entity
     */
    Entity mergeEntities(Entity one, Entity two, Map<Field, Field> fieldMapping);

    /**
     * merges two modules into one module
     * @param one one of the modules
     * @param two the other module
     * @return a new module comprised of the other two
     */
    Module mergeModules(Module one, Module two);

    /**
     * merges two fields into one field
     * @param one the first field to merge
     * @param two the second field to merge
     * @return the new field
     */
    Field mergeFields(Field one, Field two);

}
