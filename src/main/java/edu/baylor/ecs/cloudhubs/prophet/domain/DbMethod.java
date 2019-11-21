package edu.baylor.ecs.cloudhubs.prophet.domain;

import edu.baylor.ecs.cloudhubs.prophet.domain.tokenType.DbStatement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbMethod {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Relationship(type = "NEXT_TOKEN", value = Relationship.OUTGOING)
    private DbStatement dbStatement;
}
