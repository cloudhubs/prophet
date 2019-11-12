package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@Data
@NodeEntity(label = "File")
public class DbFile {
    @Id
    @GeneratedValue
    private Long id;

    String name;

    @Relationship(type = "FILE_CLASS")
    Set<DbClass> classes = new HashSet<>();

    @Relationship(type = "FILE_FUNCTION")
    Set<DbFunction> functions = new HashSet<>();

    // If this file is a directory
    @Relationship(type = "FILE_FILE")
    Set<DbFile> files = new HashSet<>();

    @Relationship(type = "FILE_IMPORT")
    Set<DbImport> imports = new HashSet<>();

//    @Relationship(type = "FILE_HAS_MODULE", direction = Relationship.INCOMING)
//    DbModule module;
//
//    @Relationship(type = "FILE_HAS_FILE", direction = Relationship.INCOMING)
//    DbFile file;
}
