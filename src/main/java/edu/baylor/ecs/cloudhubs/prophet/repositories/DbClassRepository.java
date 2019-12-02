package edu.baylor.ecs.cloudhubs.prophet.repositories;

import edu.baylor.ecs.cloudhubs.prophet.domain.DbClass;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface DbClassRepository extends Neo4jRepository<DbClass, Long> {
    Optional<DbClass> findByName(@Param("name") String name);

    Iterable<Long> deleteByName(@Param("name") String name);

    @Query("MATCH (n { name: {oldName} }) SET n.name = {newName} RETURN n")
    Optional<DbClass> setDbClassByName(@Param("oldName") String oldName, @Param("newName") String newName);

    @Query("MATCH (n:DbClass)-[:IS_AN_ENTITY]->(m:DbEntity)")
    Iterable<DbClass> getAllEntityClasses();
}
