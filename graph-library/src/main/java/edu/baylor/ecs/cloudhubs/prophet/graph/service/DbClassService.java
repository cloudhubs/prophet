package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbClassRepository;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.parser.JPClassType;

import java.util.Optional;

public class DbClassService {
    private final DbClassRepository repository;

    public DbClassService(DbClassRepository repository) {
        this.repository = repository;
    }

    public void createClass(String className, JPClassType classType){
        DbClass dbClass = new DbClass();
        dbClass.setName(className);

        // check if no entity exists with name
        Optional<DbClass> c = repository.findByName(dbClass.getName());
        if(c.isPresent()) {
            throw new ConstraintViolationException("DbClass with name already exists");
        }

        repository.save(dbClass);
    }

    public Iterable<DbClass> getAllClasses() {
        return repository.findAll();
    }

    public DbClass getClass(String name) {
        return repository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Class with name not found"));
    }

    public void changeName(String oldName, String newName) {
        // check if oldName exists
        if(!repository.findByName(oldName).isPresent()) {
            throw new ConstraintViolationException("DbModule with name does not exist");
        }

        // check if no system with new name exits
        if(repository.findByName(newName).isPresent()) {
            throw new ConstraintViolationException("DbModule with name already exists");
        }

        repository.setDbClassByName(oldName, newName);
    }

    public void deleteClass(String moduleName) {
        repository.deleteByName(moduleName);
    }
}
