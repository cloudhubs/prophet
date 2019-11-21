package edu.baylor.ecs.cloudhubs.prophet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.baylor.ecs.cloudhubs.prophet.domain.relationship.HasAClassRel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;
import java.util.Objects;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbClass {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Boolean isGlobal; //String, Integer, Long, ...

    @JsonIgnoreProperties("dbClass")
    @Relationship(type = "HAS_A_CLASS", direction = Relationship.INCOMING)
    private HasAClassRel moduleRel;

    @Relationship(value = "HAS_A_CLASS", direction = Relationship.INCOMING)
    private List<DbVariable> dbClass;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbClass dbClass = (DbClass) o;

        return name.equals(dbClass.name) && moduleRel.getModule().equals(dbClass.getModuleRel().getModule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
