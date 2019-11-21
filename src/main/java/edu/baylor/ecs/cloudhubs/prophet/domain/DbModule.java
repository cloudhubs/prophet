package edu.baylor.ecs.cloudhubs.prophet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.baylor.ecs.cloudhubs.prophet.domain.relationship.HasAModuleRel;
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

@NodeEntity
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

    public DbModule() {
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

    public HasAModuleRel getSystemRel() {
        return systemRel;
    }

    public void setSystemRel(HasAModuleRel systemRel) {
        this.systemRel = systemRel;
    }

    public Set<DbClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<DbClass> classes) {
        this.classes = classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbModule module = (DbModule) o;

        // make sure check is performed in reference to a system
        return name.equals(module.name) && systemRel.getSystem().equals(module.getSystemRel().getSystem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
