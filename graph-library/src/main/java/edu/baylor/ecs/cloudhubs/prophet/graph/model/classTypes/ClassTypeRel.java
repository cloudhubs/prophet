package edu.baylor.ecs.cloudhubs.prophet.graph.model.classTypes;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RelationshipEntity(type = "HAS_A_CLASS")
public class ClassTypeRel {

    @StartNode
    private ClassType classType;

    @EndNode
    private DbClass dbClass;

    private ClassTypeEnum classTypeEnum;
}
