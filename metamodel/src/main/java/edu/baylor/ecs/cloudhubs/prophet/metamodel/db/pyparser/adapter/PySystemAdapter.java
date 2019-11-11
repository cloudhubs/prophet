package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.adapter;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.PySystem;

public class PySystemAdapter extends DbSystem {
    private final PySystem pySystem;

    public PySystemAdapter(PySystem pySystem) {
        this.pySystem = pySystem;
    }

    @Override
    public String getName() {
        return pySystem.getName();
    }
}
