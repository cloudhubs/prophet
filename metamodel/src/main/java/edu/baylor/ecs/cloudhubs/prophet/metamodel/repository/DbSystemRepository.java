package edu.baylor.ecs.cloudhubs.prophet.metamodel.repository;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbSystem;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

//@RepositoryRestResource(collectionResourceRel = "systems", path = "systems")
public interface DbSystemRepository extends Neo4jRepository<DbSystem, Long> {
    Optional<DbSystem> findByName(@Param("name") String name);

    Iterable<Long> deleteByName(@Param("name") String name);

    @Query("MATCH (n { name: {oldName} }) SET n.name = {newName} RETURN n")
    Optional<DbSystem> setDbSystemNameByName(@Param("oldName") String oldName, @Param("newName") String newName);
}
