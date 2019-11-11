package edu.baylor.ecs.cloudhubs.prophet.metamodel.service;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.BoundedContext;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;
import org.springframework.stereotype.Service;

@Service
public class BoundedContextDatabaseService {


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
        return null;
        //return FakeDataFactory.getSystemContext();
    }

    public static BoundedContext createBoundedContext(BoundedContext boundedContext){
        //ToDo: persist context
        return boundedContext;
    }

}
