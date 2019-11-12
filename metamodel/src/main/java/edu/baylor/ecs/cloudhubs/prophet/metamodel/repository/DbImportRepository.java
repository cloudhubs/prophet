package edu.baylor.ecs.cloudhubs.prophet.metamodel.repository;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbImport;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DbImportRepository extends Neo4jRepository<DbImport, Long> {
}
