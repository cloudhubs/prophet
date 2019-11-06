package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.singletons;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.EnumString;

@NodeEntity
public class Primitive {

    @GeneratedValue @Id
    private Long id;

    @EnumString(value = PrimitiveType.class)
    private String type;

    public Primitive(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
