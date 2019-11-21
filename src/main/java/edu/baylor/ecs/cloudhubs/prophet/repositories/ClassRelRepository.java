package edu.baylor.ecs.cloudhubs.prophet.repositories;

import edu.baylor.ecs.cloudhubs.prophet.domain.relationship.HasAClassRel;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ClassRelRepository extends Neo4jRepository<HasAClassRel, Long> {
    Optional<HasAClassRel> findBySystemNameAndModuleNameAndClazzName(@Param("systemName") String systemName, @Param("moduleName") String moduleName, @Param("clazzName") String clazzName);
}
