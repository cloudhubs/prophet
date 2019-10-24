package edu.baylor.ecs.cloudhubs.prophet.graph.repository;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DbModuleRepository extends Neo4jRepository<DbModule, Long> {
    Optional<DbModule> findByName(@Param("name") String name);

    void deleteByName(@Param("name") String name);

    @Query("update DbModule u set u.name = ?2 where u.name = ?1")
    Optional<DbModule> setDbModuleNameByName(String oldName, String newName);

    @Query("update DbModule u set u.name = ?2 where u.id = ?1")
    Optional<DbModule> setDbModuleNameById(long id, String newName);
}
