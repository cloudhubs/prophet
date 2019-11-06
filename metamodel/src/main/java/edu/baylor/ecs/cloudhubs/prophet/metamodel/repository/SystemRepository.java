package edu.baylor.ecs.cloudhubs.prophet.metamodel.repository;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.System;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.neo4j.annotation.Query;

import java.util.List;

public interface SystemRepository extends CrudRepository<System, Long> {

    @Query("MATCH (n) DETACH DELETE n;")
    void deleteAll();

    @Query("START n=node(*) MATCH (n)-[r]->(m) RETURN n,r,m;")
    List<Object> getAllConnected();

    @Query("MATCH (n) RETURN n;")
    List<Object> getAllNodes();
}