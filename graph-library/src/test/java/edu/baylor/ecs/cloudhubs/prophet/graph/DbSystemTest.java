package edu.baylor.ecs.cloudhubs.prophet.graph;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.DbSystemService;
import edu.baylor.ecs.cloudhubs.prophet.graph.service.EmbeddedDb;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Unit test for DBSystem
 */

@RunWith(SpringRunner.class)
@SpringBootTest()
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DbSystemTest {

    @Autowired
    private EmbeddedDb embeddedDb;

    @Autowired
    private DbSystemService dbSystemService;

    @Test
    public void aContextLoads() {
        Assertions.assertThat(embeddedDb.registerDb()).isNotNull();
    }

    @Test
    public void bCreateSystem() {
        dbSystemService.createByName("SystemA");
        Assertions.assertThat(dbSystemService.findByName().isPresent()).isTrue();
    }

    @Test
    public void cGetAll() {
        List<DbSystem> dbSystems = (List) dbSystemService.getAll();
        Assertions.assertThat(dbSystems.size()).isEqualTo(1);
    }

    @Test
    public void eUpdate() {
        DbSystem dbSystem = dbSystemService.getSystem("SystemA");
        Assertions.assertThat(dbSystem.getName()).isEqualTo("SystemA");
        dbSystem = dbSystemService.changeName(dbSystem.getName(), "SystemB");
        Assertions.assertThat(dbSystem.getName()).isEqualTo("SystemB");
    }

    @Test
    public void fDelete() {
        List<Long> l = (List) dbSystemService.deleteSystem("SystemB");
        Assertions.assertThat(l.size()).isEqualTo(1);
    }

    @SpringBootApplication
    static class TestConfiguration {
    }

}
