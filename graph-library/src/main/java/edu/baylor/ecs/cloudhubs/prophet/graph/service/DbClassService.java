package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbClassRepository;
import org.springframework.stereotype.Service;

@Service
public class DbClassService {
    private final DbClassRepository repository;
    private final DbModuleService moduleService;

    public DbClassService(DbClassRepository repository, DbModuleService moduleService) {
        this.repository = repository;
        this.moduleService = moduleService;
    }

    public void createByName(String moduleName, String className){
        DbClass dbClass = new DbClass();
        dbClass.setName(className);

        // get module
        DbModule module = moduleService.getModule(moduleName);

        // check if no entity exists with name and systemName
        if(module.getClasses().contains(dbClass)) {
            throw new ConstraintViolationException("Module has class with same name");
        }

        repository.save(dbClass);
    }

    public Iterable<DbClass> getAll() {
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
