package edu.baylor.ecs.cloudhubs.prophet.service;

import edu.baylor.ecs.cloudhubs.prophet.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.repository.DbModuleRepository;
import org.neo4j.cypher.InvalidArgumentException;
import org.springframework.stereotype.Service;

@Service
public class DbModuleService {
    private final DbModuleRepository repository;

    public DbModuleService(DbModuleRepository repository) {
        this.repository = repository;
    }

    public Iterable<DbModule> getAllModules() {
        return repository.findAll();
    }

    public DbModule getModule(String name) {
        return repository.findByName(name);
    }

    public DbModule getModule(long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Module with id not found"));
    }

    public void createModule(String moduleName) {
        DbModule module = new DbModule();
        module.setName(moduleName);

        if (validateModule(module)) {
            repository.save(module);
        } else {
            throw new IllegalStateException("module name must be unique");
        }
    }

    private boolean validateModule(DbModule module) {
        return true;
    }

    public void changeName(String oldName, String newName) {
        repository.setDbModuleNameByName(oldName, newName);
    }

    public void changeName(long id, String newName) {
        repository.setDbModuleNameById(id, newName);
    }

    public void deleteModule(String moduleName) {
        repository.deleteByName(moduleName);
    }

    public void deleteModule(long id) {
        repository.deleteById(id);
    }
}
