package edu.baylor.ecs.cloudhubs.prophet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.baylor.ecs.cloudhubs.prophet.model.relationship.HasAClassRel;
import edu.baylor.ecs.cloudhubs.prophet.model.relationship.HasAModuleRel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbClass {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @JsonIgnoreProperties("dbClass")
    @Relationship(type = "HAS_A_CLASS", direction = Relationship.INCOMING)
    private List<HasAClassRel> moduleClasses;
}
