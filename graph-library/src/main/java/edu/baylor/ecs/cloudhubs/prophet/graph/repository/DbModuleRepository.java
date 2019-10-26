package edu.baylor.ecs.cloudhubs.prophet.graph.repository;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DbModuleRepository extends Neo4jRepository<DbModule, Long> {
    Optional<DbModule> findByName(@Param("name") String name);

    Iterable<Long> deleteByName(@Param("name") String name);

    @Query("MATCH (n { name: {oldName} }) SET n.name = {newName} RETURN n")
    Optional<DbSystem> setDbModuleNameByNameAndSystemName(@Param("systemName") String systemName, @Param("oldName") String oldName, @Param("newName") String newName);
}
