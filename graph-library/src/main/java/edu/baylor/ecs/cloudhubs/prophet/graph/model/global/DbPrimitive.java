package edu.baylor.ecs.cloudhubs.prophet.graph.model.global;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbPrimitive {

    @Id
    @GeneratedValue
    private Long id;

}
