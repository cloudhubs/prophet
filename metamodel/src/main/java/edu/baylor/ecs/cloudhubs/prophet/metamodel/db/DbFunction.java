package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@Data
@NodeEntity
public class DbFunction {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "FUNCTION_IMPORT")
    Set<DbImport> imports = new HashSet<>();

    @Relationship(type = "FUNCTION_FUNCTION")
    Set<DbFunction> functions = new HashSet<>();

    // If this file is a directory
    @Relationship(type = "FUNCTION_CLASS")
    Set<DbClass> classes = new HashSet<>();

    @Relationship(type = "FUNCTION_HAS_FILE", direction = Relationship.INCOMING)
    DbFile file;

    @Relationship(type = "FUNCTION_HAS_FUNCTION", direction = Relationship.INCOMING)
    DbFunction function;

    @Relationship(type = "FUNCTION_HAS_CLASS", direction = Relationship.INCOMING)
    DbClass dbClass;
}
