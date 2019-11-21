package edu.baylor.ecs.cloudhubs.prophet.domain.tokenType;

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
public class DbStatement {
    @Id
    @GeneratedValue
    private Long id;

    @Relationship(value = "NEXT_STATEMENT", direction = Relationship.OUTGOING)
    private DbStatement dbStatement;

}
