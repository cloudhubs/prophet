package edu.baylor.ecs.cloudhubs.prophet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.stereotype.Indexed;

import javax.validation.constraints.NotNull;

@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbSystem {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;
}
