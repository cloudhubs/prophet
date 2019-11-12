package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "System")
@Data
public class DbSystem {
    @Id
    @GeneratedValue
    private Long id;

    @Index(unique = true)
    String name;

    @Relationship(type = "SYSTEM_MODULE")
    Set<DbModule> modules = new HashSet<>();
}
