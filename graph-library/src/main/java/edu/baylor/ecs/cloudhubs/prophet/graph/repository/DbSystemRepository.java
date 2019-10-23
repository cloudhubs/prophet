package edu.baylor.ecs.cloudhubs.prophet.graph.repository;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@RepositoryRestResource(collectionResourceRel = "systems", path = "systems")
public interface DbSystemRepository extends Neo4jRepository<DbSystem, Long> {
    Optional<DbSystem> findByName(@Param("name") String name);

    Iterable<Long> deleteByName(@Param("name") String name);

    @Query("MATCH (n { name: {oldName} }) SET n.name = {newName} RETURN n")
    Optional<DbSystem> setDbSystemNameByName(@Param("oldName") String oldName,@Param("newName") String newName);

    @Query("update DbSystem u set u.name = ?2 where u.id = ?1")
    Optional<DbSystem> setDbSystemNameById(long id, String newName);
}
