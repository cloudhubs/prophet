package edu.baylor.ecs.cloudhubs.prophet.domain;

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
public class DbVariable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(value = "HAS_A_CLASS", direction = Relationship.INCOMING)
    private DbClass dbClass;

    @Relationship(value = "HAS_A_METHOD")
    private DbMethod method;

    @Relationship(value = "HAS_AN_ANNOTATION")
    private List<DbAnnotation> annotations;


}
