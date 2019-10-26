package edu.baylor.ecs.cloudhubs.prophet.graph.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.relationship.HasAModuleRel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbModule {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @JsonIgnoreProperties("dbModule")
    @Relationship(type = "HAS_A_MODULE", direction = Relationship.INCOMING)
    private HasAModuleRel systemRel;

    @Relationship(type = "HAS_A_CLASS", direction = Relationship.OUTGOING)
    private Set<DbClass> classes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbModule module = (DbModule) o;
        return name.equals(module.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
