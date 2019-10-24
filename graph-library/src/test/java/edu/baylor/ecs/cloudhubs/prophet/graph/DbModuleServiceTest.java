package edu.baylor.ecs.cloudhubs.prophet.graph;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbModuleService;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.EmbeddedDb;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext.Entity;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class DbModuleServiceTest {
    @Autowired
    private DbModuleService service;

    @Test
    public void getAllEmpty() {
        List modules = (List) service.getAll();
        Assertions.assertThat(modules.size()).isEqualTo(0);
    }

    @Test
    public void getAll() {
        service.createByName("ModuleA");
        List modules = (List) service.getAll();
        Assertions.assertThat(modules.size()).isEqualTo(1);
    }

    @Test
    public void getAll_2() {
        service.createByName("ModuleA");
        List modules = (List) service.getAll();
        Assertions.assertThat(modules.size()).isEqualTo(1);
    }

    @Test
    public void getModule() {
        service.createByName("ModuleA");
        DbModule module = service.getModule("ModuleA");
        Assertions.assertThat(module).isNotEqualTo(null);
    }

    @Test
    public void createModule() {
        service.createByName("ModuleA");
        service.createByName("ModuleB");
        service.createByName("ModuleC");
        service.createByName("ModuleD");

        List modules = (List) service.getAll();
        Assertions.assertThat(modules.size()).isEqualTo(4);
    }

    @Test
    public void createModuleDuplicate() {
        service.createByName("ModuleA");

        Throwable thrown = catchThrowable(() -> service.createByName("ModuleA"));
        Assertions.assertThat(thrown).isInstanceOf(ConstraintViolationException.class);
        ;

        List modules = (List) service.getAll();
        Assertions.assertThat(modules.size()).isEqualTo(1);
    }

    @Test
    public void changeName() {
        service.createByName("ModuleA");
        service.changeName("ModuleA", "ModuleB");

        Throwable thrown = catchThrowable(() -> service.getModule("ModuleA"));
        Assertions.assertThat(thrown).isInstanceOf(EntityNotFoundException.class);

        DbModule module2 = service.getModule("ModuleB");
        Assertions.assertThat(module2).isNotEqualTo(null);
    }

    @Test
    public void deleteModule() {
        service.createByName("ModuleA");
        DbModule module = service.getModule("ModuleA");
        Assertions.assertThat(module).isNotEqualTo(null);

        service.deleteModule("ModuleA");

        Throwable thrown = catchThrowable(() -> service.getModule("ModuleA"));
        Assertions.assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}