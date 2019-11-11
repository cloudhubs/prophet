package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.adapter;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.PyClass;

public class PyClassAdapter extends DbClass {
    private final PyClass pyClass;

    public PyClassAdapter(PyClass pyClass) {
        this.pyClass = pyClass;
    }

    @Override
    public String getName() {
        return pyClass.getName();
    }
}
