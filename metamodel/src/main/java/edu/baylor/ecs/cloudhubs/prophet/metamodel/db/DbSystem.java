package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Data
public class DbSystem {
    @Id
    @GeneratedValue
    private Long id;

    String name;

    @Relationship(type = "SYSTEM_MODULE")
    Set<DbModule> modules = new HashSet<>();
}
