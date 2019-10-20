package edu.baylor.ecs.cloudhubs.prophet.service;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;

public class DatabaseFacade {

    /**
     *
     * It retrieves following data structure from database based on system name
     * System1
     *  -Module1
     *      - EntityClass1
     *          - Field1
     *          - Field2
     *      - EntityClass2
     *  -Module2
     *      -EntityClass3 ...
     * @return
     */
    public static SystemContext getAllEntityClassesInSystem(String name){
        return new SystemContext();
    }

    /**
     * System -> DbBoundedContext
     * MergedEntity::createNew
     * Entity -(merged)-> Entity
     * @param boundedContext
     * @return
     */
    public static BoundedContext createBoundedContext(BoundedContext boundedContext){
        //ToDo: persist context
        return boundedContext;
    }

    // Java Source Code Parser


    // Java Bytecode Parser

    // -- data for bounded context

    // -- data for semantics


    // Python Source Code Parser

    // Semantic detection
}
