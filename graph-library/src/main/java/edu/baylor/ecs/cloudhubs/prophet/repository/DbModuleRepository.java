package edu.baylor.ecs.cloudhubs.prophet.repository;

import edu.baylor.ecs.cloudhubs.prophet.model.DbModule;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface DbModuleRepository extends Neo4jRepository<DbModule, Long> {
    DbModule findByName(@Param("name") String name);

    void deleteByName(@Param("name") String name);

    @Query("update DbModule u set u.name = ?2 where u.name = ?1")
    void setDbModuleNameByName(String oldName, String newName);

    @Query("update DbModule u set u.name = ?2 where u.id = ?1")
    void setDbModuleNameById(long id, String newName);
}
