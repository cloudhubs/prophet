package edu.baylor.ecs.cloudhubs.prophet.metamodel.repository;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbClass;
import org.springframework.data.repository.CrudRepository;

public interface DbClassRepository extends CrudRepository<DbClass, Long> {
}
