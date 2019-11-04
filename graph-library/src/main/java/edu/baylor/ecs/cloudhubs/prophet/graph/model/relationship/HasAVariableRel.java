package edu.baylor.ecs.cloudhubs.prophet.graph.model.relationship;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbVariable;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.enums.ClassVariableRelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RelationshipEntity(type = "HAS_A_VARIABLE")
public class HasAVariableRel {

    @Id
    @GeneratedValue
    private Long id;

    private ClassVariableRelType classVariableRelType;

    @StartNode
    private DbClass dbClass;

    @EndNode
    private DbVariable dbVariable;
}
