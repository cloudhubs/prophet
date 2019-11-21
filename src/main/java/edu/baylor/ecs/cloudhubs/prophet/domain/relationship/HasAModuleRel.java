package edu.baylor.ecs.cloudhubs.prophet.domain.relationship;

import edu.baylor.ecs.cloudhubs.prophet.domain.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.domain.DbSystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RelationshipEntity(type = "HAS_A_MODULE")
public class HasAModuleRel {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private DbSystem system;

    @EndNode
    private DbModule module;
}
