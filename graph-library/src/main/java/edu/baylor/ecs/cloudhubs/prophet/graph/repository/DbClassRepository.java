package edu.baylor.ecs.cloudhubs.prophet.graph.repository;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbClass;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface DbClassRepository extends Neo4jRepository<DbClass, Long> {
    DbClass findByName(@Param("name") String name);

    void deleteByName(@Param("name") String name);

    @Query("update DbClass u set u.name = ?2 where u.name = ?1")
    void setDbClassByName(String oldName, String newName);

    @Query("update DbClass u set u.name = ?2 where u.id = ?1")
    void setDbClassNameById(long id, String newName);
}
