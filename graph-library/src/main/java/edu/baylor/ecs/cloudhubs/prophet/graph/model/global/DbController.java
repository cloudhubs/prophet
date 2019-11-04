package edu.baylor.ecs.cloudhubs.prophet.graph.model.global;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbController {
    @Id
    @GeneratedValue
    private Long id;

}
