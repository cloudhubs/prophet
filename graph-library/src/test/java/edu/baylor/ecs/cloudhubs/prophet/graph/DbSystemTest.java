package edu.baylor.ecs.cloudhubs.prophet.graph;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbSystemService;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.EmbeddedDb;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

/**
 * Unit test for DBSystem
 */

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class DbSystemTest {

    @Autowired
    private EmbeddedDb embeddedDb;

    @Autowired
    private DbSystemService dbSystemService;

    @Test
    public void contextLoads() {
        Assertions.assertThat(embeddedDb.registerDb()).isNotNull();
    }

    @Test
    public void createSystem() {
        dbSystemService.createByName("SystemA");
        Assertions.assertThat(dbSystemService.findByName("SystemA")).isNotEqualTo(null);
    }

    @Test
    public void createSystemDuplicate() {
        dbSystemService.createByName("SystemA");

        Throwable thrown = catchThrowable(() -> dbSystemService.createByName("SystemA"));
        Assertions.assertThat(thrown).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void createSystemWithNull() {
        Throwable thrown = catchThrowable(() -> dbSystemService.createByName(null));
        Assertions.assertThat(thrown).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void getAll() {
        dbSystemService.createByName("SystemA");

        List<DbSystem> dbSystems = (List) dbSystemService.getAll();
        Assertions.assertThat(dbSystems.size()).isEqualTo(1);
    }

    @Test
    public void eUpdate() {
        dbSystemService.createByName("SystemA");

        DbSystem dbSystem = dbSystemService.getSystem("SystemA");
        Assertions.assertThat(dbSystem.getName()).isEqualTo("SystemA");

        dbSystem = dbSystemService.changeName(dbSystem.getName(), "SystemB");
        Assertions.assertThat(dbSystem.getName()).isEqualTo("SystemB");
    }

    @Test
    public void fDelete() {
        dbSystemService.createByName("SystemA");

        List<Long> l = (List) dbSystemService.deleteSystem("SystemA");
        Assertions.assertThat(l.size()).isEqualTo(1);
    }

    @SpringBootApplication
    static class TestConfiguration {
    }

}
