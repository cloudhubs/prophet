package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.adapter;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbImport;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.PyImport;

public class PyImportAdapter extends DbImport {
    private final PyImport pyImport;

    public PyImportAdapter(PyImport pyImport) {
        this.pyImport = pyImport;
    }

    @Override
    public String getName() {
        return pyImport.getName();
    }
}
