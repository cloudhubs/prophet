package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

import java.util.List;

@Data
public class PyClass {
    @Id
    @GeneratedValue
    private Long id;

    String name;
    String type;
    String component_type;
    List<PyImport> imports;
    List<PyBase> bases;
    List<PyClass> classes;
    List<PyFunction> functions;
}
