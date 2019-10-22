package edu.baylor.ecs.cloudhubs.prophet.repository;

import edu.baylor.ecs.cloudhubs.prophet.model.DbSystem;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface DbSystemRepository extends Neo4jRepository<DbSystem, Long> {
    DbSystem findByName(@Param("name") String name);

    void deleteByName(@Param("name") String name);

    @Query("update DbSystem u set u.name = ?2 where u.name = ?1")
    void setDbSystemNameByName(String oldName, String newName);

    @Query("update DbSystem u set u.name = ?2 where u.id = ?1")
    void setDbSystemNameById(long id, String newName);
}
