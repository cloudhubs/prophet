package edu.baylor.ecs.cloudhubs.prophet.metamodel.repository;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbFunction;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DbFunctionRepository extends Neo4jRepository<DbFunction, Long> {}
