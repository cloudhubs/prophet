package edu.baylor.ecs.cloudhubs.prophet.repositories;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DbSystemTest.class,
        DbModuleServiceTest.class,
        ModuleRelRepositoryTest.class,
        DbClassServiceTest.class,
        JParserAdapterServiceTest.class//,
//        DbBoundedContextTest.class
})
public class GraphLibrarySuite {

}

