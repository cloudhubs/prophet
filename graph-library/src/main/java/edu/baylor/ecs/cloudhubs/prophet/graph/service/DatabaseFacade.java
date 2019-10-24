package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;

public class DatabaseFacade {

    /**
     * It retrieves following data structure from database based on system name
     * System1
     * -Module1
     * - EntityClass1
     * - Field1
     * - Field2
     * - EntityClass2
     * -Module2
     * -EntityClass3 ...
     *
     * @return
     */
    public static SystemContext getAllEntityClassesInSystem(String name) {
        return new SystemContext();
    }

    /**
     * MergedEntity::createNew
     * -- merged entity has associations to fields of merging entities
     * Entity -(mergedTo)-> MergedEntity
     * System -> MergedEntity
     * DbBoundedContext -> MergedEntity + Other entities
     *
     * @param boundedContext
     * @return
     */
    public static BoundedContext createBoundedContext(BoundedContext boundedContext) {
        //ToDo: persist context
        return boundedContext;
    }

    public static BoundedContext updateBoundedContext(BoundedContext boundedContext) {
        return boundedContext;
    }

    public static BoundedContext deleteBoundedContext(BoundedContext boundedContext) {
        return boundedContext;
    }

    public static BoundedContext getBoundedContext() {
        return new BoundedContext();
    }

    // Java Source Code Parser


    // Java Bytecode Parser

    // -- data for bounded context

    // -- data for semantics


    // Python Source Code Parser

    // Semantic detection
}
