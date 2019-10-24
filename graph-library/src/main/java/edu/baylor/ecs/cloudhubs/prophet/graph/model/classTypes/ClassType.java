package edu.baylor.ecs.cloudhubs.prophet.graph.model.classTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class ClassType {

    @Id
    @GeneratedValue
    private Long id;

}
