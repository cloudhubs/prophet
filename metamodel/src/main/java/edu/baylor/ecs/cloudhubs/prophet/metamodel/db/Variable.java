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
    private DbClass classType;

    @Relationship(type = "VARIABLE_MERGED", direction = Relationship.UNDIRECTED)
    private Set<Variable> mergedVariables = new HashSet<>();

    @Relationship(type = "HAS_TYPE", direction = Relationship.UNDIRECTED)
    private Primitive primitiveType;

    @Relationship(value = "HAS_TYPE", direction = Relationship.UNDIRECTED)
    private DbClass dbClass;

    public Variable(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DbClass getClassType() {
        return classType;
    }

    public void setClassType(DbClass classType) {
        this.classType = classType;
    }

    public Set<Variable> getMergedVariables() {
        return mergedVariables;
    }

    public void setMergedVariables(Set<Variable> mergedVariables) {
        this.mergedVariables = mergedVariables;
    }

    public Primitive getPrimitiveType() {
        return primitiveType;
    }

    public void setPrimitiveType(Primitive primitiveType) {
        this.primitiveType = primitiveType;
    }

    public DbClass getDbClass() {
        return dbClass;
    }

    public void setDbClass(DbClass dbClass) {
        this.dbClass = dbClass;
    }
}
