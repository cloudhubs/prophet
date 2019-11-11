package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.pyparser;

import lombok.Data;

import java.util.List;

@Data
public class PyModule {
    String name;
    String full_name;
    String relative_name;
    String type;
    List<PyImport> imports;
    List<PyClass> classes;
    List<PyFunction> functions;
}
