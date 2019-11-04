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

import java.util.List;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbAnnotationType {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonIgnoreProperties("dbAnnotationType")
    @Relationship(type = "HAS_AN_ANNOTATION_TYPE", direction = Relationship.INCOMING)
    private List<DbAnnotation> dbAnnotations;
}
