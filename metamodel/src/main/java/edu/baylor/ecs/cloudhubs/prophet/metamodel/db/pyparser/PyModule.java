package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

import java.util.List;

@Data
public class PyModule {
    @Id
    @GeneratedValue
    private Long id;

    String name;
    String full_name;
    String relative_name;
    String type;
    List<PyImport> imports;
    List<PyClass> classes;
    List<PyFunction> functions;
}
