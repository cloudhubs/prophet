package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.singletons.Entity;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class DbClass {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Relationship(type = "MERGED_FROM", direction = Relationship.UNDIRECTED)
    private Set<DbClass> dbClasses = new HashSet<>();

    @Relationship(value = "CLASS_VARIABLE", direction = Relationship.UNDIRECTED)
    private Set<Variable> variables = new HashSet<>();

    @Relationship(value = "IS_ENTITY", direction = Relationship.UNDIRECTED)
    private Entity entity;

    public DbClass(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
