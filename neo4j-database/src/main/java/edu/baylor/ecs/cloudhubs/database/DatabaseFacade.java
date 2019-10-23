/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.cloudhubs.database;

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
     *      -EntityClass3
     * @return
     */
    public static SystemContext getAllEntityClassesInSystem(String name){
        return FakeDataFactory.getSystemContext();
    }

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
