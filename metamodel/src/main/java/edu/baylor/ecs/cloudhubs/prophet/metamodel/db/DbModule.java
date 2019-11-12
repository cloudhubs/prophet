package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Module")
@Data
public class DbModule {
    @Id
    @GeneratedValue
    private Long id;

    String name;

    @Relationship(type = "MODULE_FILE")
    Set<DbFile> files = new HashSet<>();

    @Relationship(type = "MODULE_MODULE")
    Set<DbModule> modules = new HashSet<>();

//    @Relationship(type = "MODULE_HAS_SYSTEM", direction = Relationship.INCOMING)
//    DbSystem system;
}
