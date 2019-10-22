package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.parser.JPClassType;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbClassRepository;

public class DbClassService {
    private final DbClassRepository repository;

    public DbClassService(DbClassRepository repository) {
        this.repository = repository;
    }

    public void createClass(String className, JPClassType classType){
        DbClass dbClass = new DbClass();
        dbClass.setName(className);

        if (validateClass(dbClass)) {
            repository.save(dbClass);
        } else {
            throw new IllegalStateException("Class name must be unique");
        }
    }

    public Iterable<DbClass> getAllClasses() {
        return repository.findAll();
    }

    public DbClass getClass(String name) {
        return repository.findByName(name);
    }

    public DbClass getClass(long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Class with id not found"));
    }

    private boolean validateClass(DbClass dbClass) {
        return true;
    }

    public void changeName(String oldName, String newName) {
        repository.setDbClassByName(oldName, newName);
    }

    public void changeName(long id, String newName) {
        repository.setDbClassNameById(id, newName);
    }

    public void deleteClass(String moduleName) {
        repository.deleteByName(moduleName);
    }

    public void deleteClass(long id) {
        repository.deleteById(id);
    }


}
