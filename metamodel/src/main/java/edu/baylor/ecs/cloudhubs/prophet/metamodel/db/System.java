package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class System {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "HAS_A_MODULE", direction = Relationship.OUTGOING)
    private Set<Module> modules = new HashSet<>();

    public System(){}

    public System(String name){
        this.name = name;
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

    public void addModule(Module m){
        this.modules.add(m);
    }

    @Override
    public String toString() {
        return "System{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", modules=" + modules +
                '}';
    }
}
