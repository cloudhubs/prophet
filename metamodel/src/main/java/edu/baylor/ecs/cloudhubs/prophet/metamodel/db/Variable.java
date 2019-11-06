package edu.baylor.ecs.cloudhubs.prophet.metamodel.db;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.singletons.Primitive;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Variable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "HAS_TYPE", direction = Relationship.UNDIRECTED)
    private Primitive primitiveType;

    @Relationship(type = "HAS_TYPE", direction = Relationship.UNDIRECTED)
    private DbClass classType;

    @Relationship(type = "VARIABLE_MERGED", direction = Relationship.UNDIRECTED)
    private Set<Variable> mergedVariables = new HashSet<>();

    @Relationship(value = "CLASS_VARIABLE", direction = Relationship.UNDIRECTED)
    private DbClass dbClass;

    public Variable(){}


}
