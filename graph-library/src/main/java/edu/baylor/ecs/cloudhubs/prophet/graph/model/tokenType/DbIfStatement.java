package edu.baylor.ecs.cloudhubs.prophet.graph.model.tokenType;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.enums.DecisionStatementSign;
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
public class DbIfStatement extends DbStatement {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "HAS_LEFT", value = Relationship.OUTGOING)
    private DbStatement dbStatementLeft;

    @Relationship(type = "HAS_RIGHT", value = Relationship.OUTGOING)
    private DbStatement dbStatementRight;

    private DecisionStatementSign decisionStatementSign;
}
