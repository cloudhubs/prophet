package edu.baylor.ecs.cloudhubs.database;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;

public class DatabaseFacade {

    /**
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
        //ToDo @Ian: create some system context, modules, entities
        SystemContext systemContext = new SystemContext();
        return systemContext;
    }

    public static BoundedContext createBoundedContext(BoundedContext boundedContext){
        //ToDo: persist context
        return boundedContext;
    }
}
