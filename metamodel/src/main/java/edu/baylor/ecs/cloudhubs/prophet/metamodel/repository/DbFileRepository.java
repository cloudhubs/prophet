package edu.baylor.ecs.cloudhubs.prophet.metamodel.repository;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbFile;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DbFileRepository extends Neo4jRepository<DbFile, Long> {}
