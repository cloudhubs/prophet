package edu.baylor.ecs.cloudhubs.prophet.service;

import edu.baylor.ecs.cloudhubs.prophet.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.repository.DbSystemRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
public class DbSystemService {

    private final DbSystemRepository repository;

    public DbSystemService(DbSystemRepository repository) {
        this.repository = repository;
    }

    public Iterable<DbSystem> getAllSystems() {
        return repository.findAll();
    }

    public DbSystem getSystem(String name) {
        return repository.findByName(name);
    }

    public DbSystem getSystem(long id) {
        return repository.findById(id).orElse(null);
    }

    public void createSystem(String systemName) {
        DbSystem dbSystem = new DbSystem();
        dbSystem.setName(systemName);

        if (validateSystem(dbSystem)) {
            repository.save(dbSystem);
        } else {
            throw new IllegalStateException("system name must be unique");
        }
    }

    private boolean validateSystem(DbSystem dbSystem) {
        return true;
    }

    public void changeName(String oldName, String newName) {
        repository.setDbSystemNameByName(oldName, newName);
    }

    public void changeName(long id, String newName) {
        repository.setDbSystemNameById(id, newName);
    }

    public void deleteSystem(String systemName) {
        repository.deleteByName(systemName);
    }

    public void deleteSystem(long id) {
        repository.deleteById(id);
    }

    /**
     * Future: Recursively delete all "direct" relationship of module
     * (module's classes, class's variables, etc.)
     *
     * @param moduleName
     */
    public void deleteModuleRelRec(String moduleName) {
        throw new NotImplementedException("deleteModuleRelRec not implemented");
    }

    // business rules

    public void createModuleRel(String moduleName) {
        throw new NotImplementedException("createModuleRel not implemented");
    }


}
