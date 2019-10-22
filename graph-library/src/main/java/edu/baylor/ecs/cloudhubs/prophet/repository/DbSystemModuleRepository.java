package edu.baylor.ecs.cloudhubs.prophet.repository;

import edu.baylor.ecs.cloudhubs.prophet.model.DbSystemModule;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface DbSystemModuleRepository extends Neo4jRepository<DbSystemModule, Long> {
    List<DbSystemModule> findAllDbSystemModuleBySystemName(String systemName);

    DbSystemModule findDbSystemModuleByModuleName(String moduleName);
}
