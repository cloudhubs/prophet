package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NodeEntity
@Data
public class DbSystem {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public DbSystem(){}

    public DbSystem(String name){
        this.name = name;
    }

    @Relationship(type = "HAS_A_MODULE", direction = Relationship.OUTGOING)
    private Set<DbModule> modulesRel = new HashSet<>();
}
