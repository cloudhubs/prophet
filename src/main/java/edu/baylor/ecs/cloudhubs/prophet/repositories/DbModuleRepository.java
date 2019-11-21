package edu.baylor.ecs.cloudhubs.prophet.repositories;

import edu.baylor.ecs.cloudhubs.prophet.domain.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.domain.DbSystem;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DbModuleRepository extends Neo4jRepository<DbModule, Long> {
    Optional<DbModule> findByName(@Param("name") String name);

    Iterable<Long> deleteByName(@Param("name") String name);

    @Query("MATCH (:DbSystem { name: {systemName} })-[:HAS_A_MODULE]->(m:DbModule {name: {oldName} }) SET m.name = {newName} RETURN m")
    Optional<DbSystem> setDbModuleNameByNameAndSystemName(@Param("systemName") String systemName, @Param("oldName") String oldName, @Param("newName") String newName);
}
