package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.adapter;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.PyApp;

public class PyAppAdapter extends DbModule {
    private final PyApp pyApp;

    public PyAppAdapter(PyApp pyApp) {
        this.pyApp = pyApp;
    }

    @Override
    public String getName() {
        return pyApp.getName();
    }
}
