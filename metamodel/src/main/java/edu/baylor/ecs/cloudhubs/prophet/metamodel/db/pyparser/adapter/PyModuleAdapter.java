package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.adapter;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbFile;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.PyModule;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

public class PyModuleAdapter extends DbFile {
    private final PyModule pyModule;

    public PyModuleAdapter(PyModule pyModule) {
        super();
        this.pyModule = pyModule;
    }

    @Override
    public String getName() {
        return pyModule.getName();
    }
}
