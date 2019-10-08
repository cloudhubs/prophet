package edu.baylor.ecs.cloudhubs.database;


import edu.baylor.ecs.cloudhubs.database.model.System;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.parser.JPSystem;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.neo4j.graphdb.Node;


import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
@ExtendWith(WeldJunit5Extension.class)
public class AppTest 
{

    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(SystemRepository.class).build();


    @DisplayName("Test MessageService.get()")
    @Test
    void testGet(SystemRepository systemRepository) {
        systemRepository.createDb();
        systemRepository.createSystemModules(new JPSystem("a"));
        JPSystem node = systemRepository.getByName("a");
//        weld.select(SystemRepository.class).get().createSystemModules(new JPSystem());
        assertEquals(node.getName(), "a");
        systemRepository.shutDown();
    }
}
