package edu.baylor.ecs.cloudhubs.prophet.repository;

import edu.baylor.ecs.cloudhubs.prophet.model.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.model.DbModule;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.CrudRepository;

public interface DbModuleRepository  extends Neo4jRepository<DbModule, Long> {
}
