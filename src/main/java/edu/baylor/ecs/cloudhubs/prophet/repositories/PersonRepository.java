package edu.baylor.ecs.cloudhubs.prophet.repositories;

import edu.baylor.ecs.cloudhubs.prophet.domain.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author pdtyreus
 * @author Mark Angrish
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Person findByName(String name);

}