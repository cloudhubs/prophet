package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser;


import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

import java.util.List;

@Data
public class PySystem {
    @Id
    @GeneratedValue
    private Long id;

    String name;
    List<PyApp> apps;
}
