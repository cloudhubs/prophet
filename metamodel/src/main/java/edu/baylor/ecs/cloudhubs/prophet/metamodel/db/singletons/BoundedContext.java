package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.singletons;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.System;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class BoundedContext {

    @Id @GeneratedValue private Long id;

    @Relationship(type = "HAS_BOUNDED_CONTEXT", direction = Relationship.INCOMING)
    private System system;

    public BoundedContext(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }
}
