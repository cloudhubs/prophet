package edu.baylor.ecs.cloudhubs.prophet.application;

import edu.baylor.ecs.cloudhubs.prophet.application.entity.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findByName(String name);

    @Query("START n=node(*) MATCH (n)-[r]->(m) RETURN n,r,m;")
    List<Person> queryNodesAll();
}
