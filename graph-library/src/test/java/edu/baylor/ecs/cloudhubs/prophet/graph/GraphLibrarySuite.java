package edu.baylor.ecs.cloudhubs.prophet.graph;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DbSystemTest.class,
        DbModuleServiceTest.class,
        DbClassServiceTest.class
})
public class GraphLibrarySuite {

}

