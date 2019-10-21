package edu.baylor.ecs.cloudhubs.prophet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import edu.baylor.ecs.cloudhubs.prophet.service.EmbeddedDb;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Unit test for simple App.
 */

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AppTest {

    @Autowired
    private EmbeddedDb embeddedDb;

    @Autowired
    private MyService myService;


    @Before
    public void prepareTestDatabase() {
        this.embeddedDb.registerDb();
    }

    @Test
    @DisplayName("")
    public void shouldCreateNode() {
        String message = this.myService.message();
        assertEquals(message,"system name");
    }
}
