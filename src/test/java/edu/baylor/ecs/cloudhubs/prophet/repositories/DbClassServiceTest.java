package edu.baylor.ecs.cloudhubs.prophet.repositories;

import edu.baylor.ecs.cloudhubs.prophet.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.domain.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.services.DbClassService;
import edu.baylor.ecs.cloudhubs.prophet.services.DbModuleService;
import edu.baylor.ecs.cloudhubs.prophet.services.DbSystemService;
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

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class DbClassServiceTest {
    private final String moduleName = "ModuleA";
    private final String systemName = "SystemA";
    @Autowired
    private DbClassService service;
    @Autowired
    private DbModuleService moduleService;
    @Autowired
    private DbSystemService systemService;

    @Before
    public void setUp() throws Exception {
        systemService.createByName(systemName);
        moduleService.createByName(systemName, moduleName);
    }

    @Test
    public void createClass() {
        service.createByName(systemName, moduleName, "ClassA");
        service.createByName(systemName, moduleName, "ClassB");
        service.createByName(systemName, moduleName, "ClassC");
        service.createByName(systemName, moduleName, "ClassD");

        List classes = (List) service.getAll();
        Assertions.assertThat(classes.size()).isEqualTo(4);
    }

    @Test
    public void createModuleDuplicate() {
        service.createByName(systemName, moduleName, "ModuleA");

        Throwable thrown = catchThrowable(() -> service.createByName(systemName, moduleName, "ModuleA"));
        Assertions.assertThat(thrown).isInstanceOf(ConstraintViolationException.class);
        ;

        List modules = (List) service.getAll();
        Assertions.assertThat(modules.size()).isEqualTo(1);
    }

    @Test
    public void getAll() {
        service.createByName(systemName, moduleName, "ClassA");
        service.createByName(systemName, moduleName, "ClassB");
        service.createByName(systemName, moduleName, "ClassC");
        service.createByName(systemName, moduleName, "ClassD");

        List classes = (List) service.getAll();
        Assertions.assertThat(classes.size()).isEqualTo(4);
    }

    @Test
    public void testGet() {
        service.createByName(systemName, moduleName, "ClassA");
        DbClass dbClass = service.getClass(systemName, moduleName, "ClassA");
        Assertions.assertThat(dbClass).isNotEqualTo(null);
    }

    @Test
    public void changeName() {
        service.createByName(systemName, moduleName, "ClassA");
        service.changeName(systemName, moduleName, "ClassA", "ClassB");

        Throwable thrown = catchThrowable(() -> service.getClass(systemName, moduleName, "ClassA"));
        Assertions.assertThat(thrown).isInstanceOf(EntityNotFoundException.class);

        DbClass dbClass = service.getClass(systemName, moduleName, "ClassB");
        Assertions.assertThat(dbClass).isNotEqualTo(null);
    }

    @Test
    public void delete() {
        service.createByName(systemName, moduleName, "ClassA");
        DbClass dbClass = service.getClass(systemName, moduleName, "ClassA");
        Assertions.assertThat(dbClass).isNotEqualTo(null);

        service.deleteClass(systemName, moduleName, "ClassA");

        Throwable thrown = catchThrowable(() -> service.getClass(systemName, moduleName, "ClassA"));
        Assertions.assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    }

//    @SpringBootApplication
//    static class TestConfiguration {
//    }
}
