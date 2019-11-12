package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.singletons.Entity;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Class")
@Data
public class DbClass {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "CLASS_IMPORT")
    Set<DbImport> imports = new HashSet<>();

    @Relationship(type = "CLASS_FUNCTION")
    Set<DbFunction> functions = new HashSet<>();

    // If this file is a directory
    @Relationship(type = "CLASS_CLASS")
    Set<DbClass> classes = new HashSet<>();

//    @Relationship(type = "CLASS_HAS_FILE", direction = Relationship.INCOMING)
//    DbFile file;
//
//    @Relationship(type = "CLASS_HAS_FUNCTION", direction = Relationship.INCOMING)
//    DbFunction function;
}
