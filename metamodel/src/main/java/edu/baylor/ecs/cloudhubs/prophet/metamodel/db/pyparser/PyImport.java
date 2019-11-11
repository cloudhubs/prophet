package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

@Data
public class PyImport {
    @Id
    @GeneratedValue
    private Long id;

    String name;
    String type;
}
