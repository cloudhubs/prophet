package edu.baylor.ecs.cloudhubs.prophet.repositories;

import edu.baylor.ecs.cloudhubs.prophet.domain.relationship.HasAModuleRel;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ModuleRelRepository extends Neo4jRepository<HasAModuleRel, Long> {
    Optional<HasAModuleRel> findBySystemNameAndModuleName(@Param("systemName") String systemName, @Param("moduleName") String moduleName);

//    @Query("MATCH (rel:HasAModuleRel {(s:DbSystem { name: systemName})-[:HAS_A_MODULE]->(m:DbModule {name: oldName })}) SET m.name = newName RETURN m")
//    Optional<HasAModuleRel> setDbModuleNameByNameAndSystemName(@Param("systemName") String systemName, @Param("oldName") String oldName, @Param("newName") String newName);

    Long deleteBySystemNameAndModuleName(@Param("systemName") String systemName, @Param("moduleName") String moduleName);
}


