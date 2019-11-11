package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.pyparser;


import lombok.Data;

import java.util.List;

@Data
public class PySystem {
    String name;
    List<PyModule> modules;
    List<PyPackage> packages;
    String type;
}
