package edu.baylor.ecs.cloudhubs.prophet.metamodel.repository;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbClass;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.CrudRepository;

public interface DbClassRepository extends Neo4jRepository<DbClass, Long> {
}
