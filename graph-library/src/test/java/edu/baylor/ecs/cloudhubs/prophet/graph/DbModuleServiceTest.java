package edu.baylor.ecs.cloudhubs.prophet.graph;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbModuleService;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbSystemService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class DbModuleServiceTest {
    private final String systemName = "SystemA";
    @Autowired
    private DbModuleService service;
    @Autowired
    private DbSystemService systemService;

    @Before
    public void setUp() throws Exception {
        systemService.createByName(systemName);
    }

    @Test
    public void getAllEmpty() {
        List modules = (List) service.getAll();
        Assertions.assertThat(modules.size()).isEqualTo(0);
    }

    @Test
    public void getAll() {
        service.createByName(systemName, "ModuleA");
        List modules = (List) service.getAll();
        Assertions.assertThat(modules.size()).isEqualTo(1);
    }

    @Test
    public void getAllForSystem() {
        service.createByName(systemName, "ModuleA");
        Set modules = (Set) service.getAllForSystem(systemName);
        Assertions.assertThat(modules.size()).isEqualTo(1);
    }

    @Test
    public void getAll_2() {
        service.createByName(systemName, "ModuleA");
        List modules = (List) service.getAll();
        Assertions.assertThat(modules.size()).isEqualTo(1);
    }

    @Test
    public void getModule() {
        service.createByName(systemName, "ModuleA");
        DbModule module = service.getModule(systemName, "ModuleA");
        Assertions.assertThat(module).isNotEqualTo(null);
    }

    @Test
    public void createModule() {
        service.createByName(systemName, "ModuleA");
        service.createByName(systemName, "ModuleB");
        service.createByName(systemName, "ModuleC");
        service.createByName(systemName, "ModuleD");

        List modules = (List) service.getAll();
        Assertions.assertThat(modules.size()).isEqualTo(4);
    }

    @Test
    public void createModuleDuplicate() {
        service.createByName(systemName, "ModuleA");

        Throwable thrown = catchThrowable(() -> service.createByName(systemName, "ModuleA"));
        Assertions.assertThat(thrown).isInstanceOf(ConstraintViolationException.class);

        List modules = (List) service.getAll();
        Assertions.assertThat(modules.size()).isEqualTo(1);
    }

    @Test
    public void createModuleNotDuplicate() {
        service.createByName(systemName, "ModuleA");

        // Another service
        systemService.createByName("SystemB");

        service.createByName("SystemB", "ModuleA");

        Set<DbModule> modules2 = service.getAllForSystem("SystemA");
        Assertions.assertThat(modules2.size()).isEqualTo(1);
    }

    @Test
    public void changeName() {
        service.createByName(systemName, "ModuleA");
        service.changeName(systemName, "ModuleA", "ModuleB");

        Throwable thrown = catchThrowable(() -> service.getModule(systemName, "ModuleA"));
        Assertions.assertThat(thrown).isInstanceOf(EntityNotFoundException.class);

        DbModule module2 = service.getModule(systemName, "ModuleB");
        Assertions.assertThat(module2).isNotEqualTo(null);
    }

    @Test
    public void deleteModule() {
        service.createByName(systemName, "ModuleA");
        DbModule module = service.getModule(systemName, "ModuleA");
        Assertions.assertThat(module).isNotEqualTo(null);

        service.deleteModule(systemName, "ModuleA");

        Throwable thrown = catchThrowable(() -> service.getModule(systemName, "ModuleA"));
        Assertions.assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}