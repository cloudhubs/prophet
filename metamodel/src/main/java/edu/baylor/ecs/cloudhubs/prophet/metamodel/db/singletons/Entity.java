package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.singletons;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Entity {
    @Id
    @GeneratedValue
    private Long id;

    private String description;

}
