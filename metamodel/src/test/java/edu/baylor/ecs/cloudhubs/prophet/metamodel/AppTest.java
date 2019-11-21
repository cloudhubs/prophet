package edu.baylor.ecs.cloudhubs.prophet.metamodel;

import static org.junit.Assert.assertTrue;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.repository.DbClassRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */

    @Autowired
    private DbClassRepository dbClassRepository;

    @Test
    public void shouldAnswerWithTrue()
    {
        DbClass dbClass = new DbClass();
        dbClass.setName("classTest");
        dbClass = dbClassRepository.save(dbClass);
        Optional<DbClass> db2 = dbClassRepository.findById(dbClass.getId());
        assertTrue( true );
    }
}
