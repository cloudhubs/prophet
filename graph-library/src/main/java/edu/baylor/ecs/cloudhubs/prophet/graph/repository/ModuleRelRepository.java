package edu.baylor.ecs.cloudhubs.prophet.graph.repository;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.relationship.HasAModuleRel;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRelRepository extends Neo4jRepository<HasAModuleRel, Long> {
    List<HasAModuleRel> findAllDbSystemModuleBySystemName(String systemName);

    HasAModuleRel findDbSystemModuleByModuleName(String moduleName);
}