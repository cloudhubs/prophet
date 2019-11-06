package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Module {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "SYSTEM_MODULE", direction = Relationship.UNDIRECTED)
    private System system;

    @Relationship(type = "HAS_A_CLASS", direction = Relationship.UNDIRECTED)
    private Set<DbClass> classes = new HashSet<>();

    public Module(){}

    public Module(String name){
        this.name = name;
    }

    public void addClass(DbClass dbClass){
        this.classes.add(dbClass);
    }

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

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
