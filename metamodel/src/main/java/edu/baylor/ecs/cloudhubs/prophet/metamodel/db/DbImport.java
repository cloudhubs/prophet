package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Data
@NodeEntity
public class DbImport {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "IMPORT_HAS_FILE", direction = Relationship.INCOMING)
    DbFile file;

    @Relationship(type = "IMPORT_HAS_FUNCTION", direction = Relationship.INCOMING)
    DbFunction function;

    @Relationship(type = "IMPORT_HAS_CLASS", direction = Relationship.INCOMING)
    DbClass dbClass;
}
