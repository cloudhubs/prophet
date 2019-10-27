//package edu.baylor.ecs.cloudhubs.prophet.graph;
//
//import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.ConstraintViolationException;
//import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
//import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbClass;
//import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
//import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbClassService;
//import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbModuleService;
//import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbSystemService;
//import org.assertj.core.api.Assertions;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest()
//@Transactional
//public class DbClassServiceTest {
//    @Autowired
//    private DbClassService service;
//
//    @Autowired
//    private DbModuleService moduleService;
//
//    private final String moduleName = "ModuleA";
//    private final String systemName = "SystemA";
//
//    @Before
//    public void setUp() throws Exception {
//        moduleService.createByName(systemName, moduleName);
//    }
//
//    @Test
//    public void createClass() {
//        service.createByName(moduleName, "ClassA");
//        service.createByName(moduleName, "ClassB");
//        service.createByName(moduleName, "ClassC");
//        service.createByName(moduleName, "ClassD");
//
//        List classes = (List) service.getAll();
//        Assertions.assertThat(classes.size()).isEqualTo(4);
//    }
//
//    @Test
//    public void createModuleDuplicate() {
//        service.createByName(moduleName, "ModuleA");
//
//        Throwable thrown = catchThrowable(() -> service.createByName(moduleName, "ModuleA"));
//        Assertions.assertThat(thrown).isInstanceOf(ConstraintViolationException.class);
//        ;
//
//        List modules = (List) service.getAll();
//        Assertions.assertThat(modules.size()).isEqualTo(1);
//    }
//
//    @Test
//    public void getAll() {
//        service.createByName(moduleName, "ClassA");
//        service.createByName(moduleName, "ClassB");
//        service.createByName(moduleName, "ClassC");
//        service.createByName(moduleName, "ClassD");
//
//        List classes = (List) service.getAll();
//        Assertions.assertThat(classes.size()).isEqualTo(4);
//    }
//
//    @Test
//    public void testGet() {
//        service.createByName(moduleName, "ClassA");
//        DbClass dbClass = service.getClass("ClassA");
//        Assertions.assertThat(dbClass).isNotEqualTo(null);
//    }
//
//    @Test
//    public void changeName() {
//        service.createByName(moduleName, "ClassA");
//        service.changeName("ClassA", "ClassB");
//
//        Throwable thrown = catchThrowable(() -> service.getClass("ClassA"));
//        Assertions.assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
//
//        DbClass dbClass = service.getClass("ClassB");
//        Assertions.assertThat(dbClass).isNotEqualTo(null);
//    }
//
//    @Test
//    public void delete() {
//        service.createByName(moduleName, "ClassA");
//        DbClass dbClass = service.getClass("ClassA");
//        Assertions.assertThat(dbClass).isNotEqualTo(null);
//
//        service.deleteClass("ClassA");
//
//        Throwable thrown = catchThrowable(() -> service.getClass("ClassA"));
//        Assertions.assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
//    }
//
//    @SpringBootApplication
//    static class TestConfiguration {
//    }
//}