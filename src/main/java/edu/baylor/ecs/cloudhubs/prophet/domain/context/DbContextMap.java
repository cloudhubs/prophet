package edu.baylor.ecs.cloudhubs.prophet.domain.context;

import edu.baylor.ecs.cloudhubs.prophet.domain.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.domain.DbModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class DbContextMap {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name="generatedCode")
    private String generatedCode;

    @Property(name="pathToFiles")
    private String pathToFiles;

    @Relationship(type = "CREATED_FROM")
    private Set<DbClass> entityClasses;

    private DbModule dbModule;



}
