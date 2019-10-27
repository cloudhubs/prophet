package edu.baylor.ecs.cloudhubs.prophet.graph.repository;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.relationship.HasAModuleRel;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ModuleRelRepository extends Neo4jRepository<HasAModuleRel, Long> {
    Optional<HasAModuleRel> findBySystemNameAndModuleName(@Param("systemName") String systemName, @Param("moduleName") String moduleName);

//    @Query("MATCH (rel:HasAModuleRel {(s:DbSystem { name: systemName})-[:HAS_A_MODULE]->(m:DbModule {name: oldName })}) SET m.name = newName RETURN m")
//    Optional<HasAModuleRel> setDbModuleNameByNameAndSystemName(@Param("systemName") String systemName, @Param("oldName") String oldName, @Param("newName") String newName);

    Long deleteBySystemNameAndModuleName(@Param("systemName") String systemName, @Param("moduleName") String moduleName);
}


