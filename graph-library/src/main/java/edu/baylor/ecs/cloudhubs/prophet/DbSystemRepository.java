package edu.baylor.ecs.cloudhubs.prophet;


import edu.baylor.ecs.cloudhubs.prophet.model.DbSystem;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DbSystemRepository extends Neo4jRepository<DbSystem, Long> {

    DbSystem findByName(@Param("name") String name);
}