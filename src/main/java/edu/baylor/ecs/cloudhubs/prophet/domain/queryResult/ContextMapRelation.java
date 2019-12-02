package edu.baylor.ecs.cloudhubs.prophet.domain.queryResult;

import edu.baylor.ecs.cloudhubs.prophet.domain.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.domain.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.domain.DbVariable;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class ContextMapRelation {
    private DbModule module;
    private DbClass from;
    private DbVariable dbVariable;
    //ToDo: Type
    private DbClass to;

    public ContextMapRelation(){}

    public DbModule getModule() {
        return module;
    }

    public void setModule(DbModule module) {
        this.module = module;
    }

    public DbClass getFrom() {
        return from;
    }

    public void setFrom(DbClass from) {
        this.from = from;
    }

    public DbVariable getDbVariable() {
        return dbVariable;
    }

    public void setDbVariable(DbVariable dbVariable) {
        this.dbVariable = dbVariable;
    }

    public DbClass getTo() {
        return to;
    }

    public void setTo(DbClass to) {
        this.to = to;
    }
}
