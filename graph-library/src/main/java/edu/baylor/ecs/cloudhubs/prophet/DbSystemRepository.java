package edu.baylor.ecs.cloudhubs.prophet;


import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DbSystemRepository extends Neo4jRepository<DbSystem, Long> {

    DbSystem findByName(@Param("name") String name);

//    Collection<DbSystem> findByTitleLike(@Param("title") String title);

    @Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) RETURN m,r,a LIMIT {limit}")
    Collection<DbSystem> graph(@Param("limit") int limit);
}