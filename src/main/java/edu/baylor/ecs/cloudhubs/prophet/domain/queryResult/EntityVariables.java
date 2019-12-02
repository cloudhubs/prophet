package edu.baylor.ecs.cloudhubs.prophet.domain.queryResult;

import edu.baylor.ecs.cloudhubs.prophet.domain.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.domain.DbVariable;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class EntityVariables {
    private DbClass dbClass;
    private Iterable<DbVariable> variables;

    public EntityVariables(){}

    public DbClass getDbClass() {
        return dbClass;
    }

    public void setDbClass(DbClass dbClass) {
        this.dbClass = dbClass;
    }

    public Iterable<DbVariable> getVariables() {
        return variables;
    }

    public void setVariables(Iterable<DbVariable> variables) {
        this.variables = variables;
    }
}
