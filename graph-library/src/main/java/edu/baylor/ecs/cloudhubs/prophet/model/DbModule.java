package edu.baylor.ecs.cloudhubs.prophet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbModule {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @JsonIgnoreProperties("dbModule")
    @Relationship(type = "ACTED_IN", direction = Relationship.INCOMING)
    private List<DbSystemModule> systemModules;
}
