package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.pyparser;

import lombok.Data;

import java.util.List;

@Data
public class PyFunction {
    String name;
    String type;
    String component_type;
    List<String> args;
    List<PyImport> imports;
    List<PyClass> classes;
    List<PyFunction> functions;
}
