package edu.baylor.ecs.cloudhubs.prophet.model.relationship;

import edu.baylor.ecs.cloudhubs.prophet.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.model.DbSystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
