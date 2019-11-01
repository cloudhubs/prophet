package edu.baylor.ecs.cloudhubs.prophet.graph;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.relationship.HasAModuleRel;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbModuleRepository;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbSystemRepository;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.ModuleRelRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class ModuleRelRepositoryTest {

    @Autowired
    private ModuleRelRepository repository;

    @Autowired
    private DbSystemRepository systemRepository;

    @Autowired
    private DbModuleRepository moduleRepository;

    @Test
    public void save() {
        // create a system
        DbSystem system = new DbSystem();
        system.setName("SystemA");


        // create a module
        DbModule module = new DbModule();
        module.setName("ModuleA");

        // create a relationship
        HasAModuleRel rel = new HasAModuleRel();
        rel.setSystem(system);
        rel.setModule(module);

        // use their relationship to persist them
        repository.save(rel);
    }

    @Test
    public void canRetrieveSystemFromRel() {
        // create a system
        DbSystem system = new DbSystem();
        system.setName("SystemA");


        // create a module
        DbModule module = new DbModule();
        module.setName("ModuleA");

        // create a relationship
        HasAModuleRel rel = new HasAModuleRel();
        rel.setSystem(system);
        rel.setModule(module);

        // use their relationship to persist them
        repository.save(rel);

        // tests
        Assertions.assertThat(systemRepository.findByName("SystemA").isPresent()).isTrue();
        Assertions.assertThat(moduleRepository.findByName("ModuleA").isPresent()).isTrue();
    }

    @Test
    // not able to retrieve modules from the system through the relationship
    public void canFindRelInPersistedSystem() {
        // create a system
        DbSystem system = new DbSystem();
        system.setName("SystemA");


        // create a module
        DbModule module = new DbModule();
        module.setName("ModuleA");

        // create a relationship
        HasAModuleRel rel = new HasAModuleRel();
        rel.setSystem(system);
        rel.setModule(module);

        // use their relationship to persist them
        repository.save(rel);

        // test if module can be retrieved from system
        DbSystem system1 = systemRepository.findByName("SystemA").get();
        Assertions.assertThat(system1.getName()).isEqualTo("SystemA");

        system.getModulesRel().add(module);
        systemRepository.save(system);

        Assertions.assertThat(system1.getModulesRel().size()).isEqualTo(1);
        Assertions.assertThat(system1.getModulesRel()).contains(module);
    }

    @Test
    public void canFindRelInPersistedModule() {
        // create a system
        DbSystem system = new DbSystem();
        system.setName("SystemA");


        // create a module
        DbModule module = new DbModule();
        module.setName("ModuleA");

        // create a relationship
        HasAModuleRel rel = new HasAModuleRel();
        rel.setSystem(system);
        rel.setModule(module);

        // use their relationship to persist them
        repository.save(rel);

        // test if module can be retrieved from system
        DbModule module1 = moduleRepository.findByName("ModuleA").get();
        Assertions.assertThat(module1.getSystemRel().getModule().equals(module)).isTrue();
        Assertions.assertThat(module1.getSystemRel().getModule().equals(module1)).isTrue();
        Assertions.assertThat(module1.getSystemRel().getSystem().equals(system)).isTrue();
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}