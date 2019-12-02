package edu.baylor.ecs.cloudhubs.prophet.repositories;

import edu.baylor.ecs.cloudhubs.prophet.domain.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.domain.queryResult.ContextMapRelation;
import edu.baylor.ecs.cloudhubs.prophet.domain.queryResult.EntityVariables;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

public interface BoundedContextRepository {


    /**
     * Get All Entities for a Module
     * @param moduleName
     * @param systemName
     * @return
     */
    @Query("MATCH (s:SYSTEM)-[:SYSTEM_MODULE]->(mod:MODULE)-[:HAS_A_CLASS]->(n:DBCLASS)-[:CLASS_VARIABLE]->" +
            "(var:VARIABLE), (n:DBCLASS)-[r:IS_AN_ENTITY]->(en:ENTITY)\n" +
            "    WHERE s.name={systemName} AND mod.name={moduleName}\n" +
            "            return n;")
    Iterable<DbClass> getEntitiesForModuleAndSystem(@Param("systemName") String systemName,
                                                    @Param("moduleName") String moduleName);

    /**
     * Get All Entities and their Variables for a Module
     * @param systemName
     * @param moduleName
     * @return
     */
    @Query("MATCH (s:SYSTEM)-[:SYSTEM_MODULE]->(mod:MODULE)-[:HAS_A_CLASS]->(n:DBCLASS)-[:CLASS_VARIABLE]->(var:VARIABLE)\n" +
            "    WHERE s.name={systemName} AND mod.name={moduleName}\n" +
            "            return n, collect(var) as variables;")
    Iterable<EntityVariables> getEntitiesWithVariablesForModuleAndSystem(@Param("systemName") String systemName,
                                                                         @Param("moduleName") String moduleName);




    @Query("MATCH (s:SYSTEM)-[:SYSTEM_MODULE]->(mod:MODULE)-[:HAS_A_CLASS]->(n:DBCLASS)-[:CLASS_VARIABLE]->(var:VARIABLE)," +
            "(n:DBCLASS)-[r2:IS_AN_ENTITY]->(en:ENTITY), (var:VARIABLE)-[type:HAS_TYPE]->(tp:DBCLASS)\n" +
            "WHERE s.name={systemName}\n" +
            "return mod as module, n as from, collect(var) as variables, type as relType, tp as to;")
    Iterable<ContextMapRelation> getRelationsInContextMap(@Param("systemName") String systemName);

}
