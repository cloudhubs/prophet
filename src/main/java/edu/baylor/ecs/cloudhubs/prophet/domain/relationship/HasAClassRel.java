package edu.baylor.ecs.cloudhubs.prophet.domain.relationship;

import edu.baylor.ecs.cloudhubs.prophet.domain.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.domain.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.domain.DbSystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RelationshipEntity(type = "HAS_A_CLASS")
public class HasAClassRel {
    @Id
    @GeneratedValue
    private Long id;

    private DbSystem system;

    @StartNode
    private DbModule module;

    @EndNode
    private DbClass clazz;
}
