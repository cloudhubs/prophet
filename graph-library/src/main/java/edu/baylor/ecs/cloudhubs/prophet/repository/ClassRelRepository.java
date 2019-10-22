package edu.baylor.ecs.cloudhubs.prophet.repository;

import edu.baylor.ecs.cloudhubs.prophet.model.relationship.HasAModuleRel;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface ClassRelRepository extends Neo4jRepository<HasAModuleRel, Long> {
    List<HasAModuleRel> findAllDbSystemModuleBySystemName(String systemName);

    HasAModuleRel findDbSystemModuleByModuleName(String moduleName);
}
