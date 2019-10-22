package edu.baylor.ecs.cloudhubs.prophet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbSystemModule {
    @Id
    @GeneratedValue
    private Long id;

    // name of link/relationship
    private List<String> systemModules = new ArrayList<>();

    @StartNode
    private DbSystem system;

    @EndNode
    private DbModule module;
}
