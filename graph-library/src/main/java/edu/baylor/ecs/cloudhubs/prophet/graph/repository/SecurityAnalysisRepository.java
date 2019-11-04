package edu.baylor.ecs.cloudhubs.prophet.graph.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SecurityAnalysisRepository extends Neo4jRepository<Object, Long> {
    @Query("START n=node(*) MATCH (n)-[r]->(m)\n" +
            "RETURN n,r,m;")
    List<Object> queryGetAll();

    @Query("start n=node(*)\n" +
            "match (n)-[:NEXT]-()\n" +
            "return distinct n")
    List<Object> queryHavingNextRel();

    @Query("MATCH (class)-[:HAS_METHOD]->(method)\n" +
            "RETURN method")
    List<Object> queryHavingHasMethod();

    @Query("MATCH (method)-[:NEXT]->(method)\n" +
            "RETURN method\n" +
            "UNION\n" +
            "MATCH (method)-[:HAS_ROLE]-(role)\n" +
            "RETURN method")
    List<Object> queryNextWithRelToRole();

    @Query("MATCH (system)-[:HAS_MODULE]->(module)\n" +
            "    WHERE system.systemId={systemId}\n" +
            "    MATCH (module)-[:HAS_CLASS]->(class)\n" +
            "    WHERE module.moduleName={moduleName}\n" +
            "    MATCH (method)-[:HAS_ROLE]->(role)\n" +
            "    WHERE role.roleName={roleNameA}\n" +
            "    RETURN method\n" +
            "    UNION\n" +
            "    MATCH (method)-[:HAS_ROLE]-(role)\n" +
            "    WHERE role.roleName={roleNameB}\n" +
            "    RETURN method")
    List<Object> setDbModuleNameByNameAndSystemName(@Param("systemId") Long systemId,
                                                    @Param("moduleName") String moduleName,
                                                    @Param("roleNameA") String roleNameA,
                                                    @Param("roleNameB") String roleNameB);





}
