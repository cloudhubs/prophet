package edu.baylor.ecs.cloudhubs.prophet.graph.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

public class DbAnnotation {
    @Id
    @GeneratedValue
    private Long id;
    @Relationship(value = "HAS_AN_ANNOTATION_TYPE")
    private DbAnnotationType dbAnnotationType;
    private Long valueLong;
    private String valueString;
    private Integer valueInteger;
}
