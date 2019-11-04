/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.cloudhubs.database;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.*;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Module;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Fake data factory
 */
public class FakeDataFactory {

    /**
     * Factory method to generate system context for Bounded-Context module
     * @return
     */
    public static SystemContext getSystemContext(String systemName, List<Module> modules){
        return new SystemContext(systemName, modules);
    }

    public static Module getModule(String name, List<Entity> entities){
        return new Module(name, entities);
    }

    public static Entity getEntity(String name, List<Field> fields){
        return new Entity(name, fields);
    }

    public static Field getField(@NotNull String name, @NotNull String type, Set<Annotation> annotations, Entity entityReference){
        Field toReturn = new Field(type, name);
        toReturn.setAnnotations(annotations);
        toReturn.setEntityReference(entityReference);
        return toReturn;
    }

    public static Annotation getAnnotation(String name, String stringValue, int intValue){
        return new Annotation(name, stringValue, intValue);
    }
}
