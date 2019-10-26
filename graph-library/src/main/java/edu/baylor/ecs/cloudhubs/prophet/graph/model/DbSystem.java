package edu.baylor.ecs.cloudhubs.prophet.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//import javax.validation.constraints.NotNull;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbSystem {
    @Id
    @GeneratedValue
    private Long id;

    //    @NotNull
    private String name;

    @Relationship(type = "HAS_A_MODULE", direction = Relationship.OUTGOING)
    private Set<DbModule> modulesRel = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbSystem system = (DbSystem) o;
        return name.equals(system.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
