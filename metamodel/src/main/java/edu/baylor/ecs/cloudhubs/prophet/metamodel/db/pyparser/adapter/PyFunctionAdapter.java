package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.adapter;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbFunction;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.PyFunction;

public class PyFunctionAdapter extends DbFunction {
    private final PyFunction pyFunction;

    public PyFunctionAdapter(PyFunction pyFunction) {
        this.pyFunction = pyFunction;
    }

    @Override
    public String getName() {
        return pyFunction.getName();
    }
}
