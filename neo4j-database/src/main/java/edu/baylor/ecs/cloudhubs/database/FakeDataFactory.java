package edu.baylor.ecs.cloudhubs.database;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Entity;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Field;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Module;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.SystemContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Fake data factory
 */
public class FakeDataFactory {

    /**
     * Factory method to generate system context for Bounded-Context module
     * @return
     */
    public static SystemContext getSystemContext(){
        SystemContext systemContext = new SystemContext();
        systemContext.setSystemName("exam_system");
        Module testManModule = new Module();
        List<Entity> testManEntities = new ArrayList<>();
        List<Field> testManFields = new ArrayList<>();
        Entity personEntity = new Entity();
        testManModule.setEntities(testManEntities);
        return systemContext;
    }
}
