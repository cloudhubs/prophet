package edu.baylor.ecs.cloudhubs.prophet.metamodel.service;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.pyparser.PySystem;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.pyparser.adapter.PySystemAdapter;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.repository.DbSystemRepository;
import org.springframework.stereotype.Service;

@Service
public class PyMetaService {
    private final DbSystemRepository repository;

    public PyMetaService(DbSystemRepository repository) {
        this.repository = repository;
    }

    public boolean persistPyData(PySystem pySystem) {
        PySystemAdapter system = new PySystemAdapter(pySystem);
        repository.save(system);
        return true;
    }
}
