package edu.baylor.ecs.cloudhubs.prophet.graph.model.tokenType;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbMethodStatement extends DbStatement{

    @Relationship(type = "NEXT_STATEMENT", value = Relationship.OUTGOING)
    private DbStatement nextStatement;

    @Relationship(type = "REFERES_TO_METHOD", value = Relationship.OUTGOING)
    private DbMethod method;

    public void setNextStatement(DbStatement dbStatement){
        this.nextStatement = dbStatement;

    }


}
