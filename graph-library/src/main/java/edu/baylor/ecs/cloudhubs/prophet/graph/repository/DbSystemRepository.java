package edu.baylor.ecs.cloudhubs.prophet.graph.repository;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DbSystemRepository extends Neo4jRepository<DbSystem, Long> {
    Optional<DbSystem> findByName(@Param("name") String name);

    void deleteByName(@Param("name") String name);

    @Query("update DbSystem u set u.name = ?2 where u.name = ?1")
    Optional<DbSystem> setDbSystemNameByName(String oldName, String newName);

    @Query("update DbSystem u set u.name = ?2 where u.id = ?1")
    Optional<DbSystem> setDbSystemNameById(long id, String newName);
}
