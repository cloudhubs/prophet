package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

import java.util.List;

@Data
public class PyApp {
    @Id
    @GeneratedValue
    private Long id;

    String name;
    List<PyModule> modules;
    List<PyPackage> packages;
    String type;
}
