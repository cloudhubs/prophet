package edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.adapter;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbFile;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.PyPackage;

public class PyPackageAdapter extends DbFile {
    private final PyPackage pyPackage;

    public PyPackageAdapter(PyPackage pyPackage) {
        this.pyPackage = pyPackage;
    }

    @Override
    public String getName() {
        return pyPackage.getName();
    }
}
