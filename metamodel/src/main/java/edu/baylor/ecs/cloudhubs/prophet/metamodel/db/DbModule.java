package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NodeEntity
@Data
public class DbModule {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

//    @JsonIgnoreProperties("dbModule")
//    @Relationship(type = "HAS_A_MODULE", direction = Relationship.INCOMING)
//    private HasAModuleRel systemRel;

    @Relationship(type = "HAS_A_CLASS", direction = Relationship.OUTGOING)
    private Set<DbClass> classes = new HashSet<>();
}
