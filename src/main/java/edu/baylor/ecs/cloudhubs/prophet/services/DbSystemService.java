package edu.baylor.ecs.cloudhubs.prophet.services;

import edu.baylor.ecs.cloudhubs.prophet.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.domain.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.domain.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.repositories.DbSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//import edu.baylor.ecs.cloudhubs.prophet.exceptions.EntityNotFoundException;

/**
 * DbSystem Service
 */
@Service
public class DbSystemService {

    @Autowired
    private DbSystemRepository repository;

    public void createByName(String name) {
        if (name == null) {
            throw new ConstraintViolationException("DbSystem cannot be null");
        }

        DbSystem dbSystem = new DbSystem();
        dbSystem.setName(name);

        // check if no entity exists with name
        Optional<DbSystem> s = repository.findByName(dbSystem.getName());
        if (s.isPresent()) {
            throw new ConstraintViolationException("DbSystem with name already exists");
        }
        repository.save(dbSystem);
    }

    public DbSystem findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new EntityNotFoundException("System with name not found"));
    }

    public Iterable<DbSystem> getAll() {
        return repository.findAll();
    }

    /**
     * Retrieves the System of the name provided
     *
     * @param name Name of system to return
     * @return Db System matching given name
     */
    public DbSystem getSystem(String name) {
        return repository.findByName(name).orElseThrow(() -> new EntityNotFoundException("System with name not found"));
    }

    /**
     * Change name of system matching given name
     *
     * @param oldName old name of system to update
     * @param newName new name of system to update
     */
    public DbSystem changeName(String oldName, String newName) {
        if (oldName == null || newName == null) {
            throw new ConstraintViolationException("DbSystem cannot be null");
        }

        // check if oldName exists
        Optional<DbSystem> oldSystem = repository.findByName(oldName);
        if (!oldSystem.isPresent()) {
            throw new ConstraintViolationException("DbSystem with name does not exist");
        }

        // check if no system with new name exits
        if (repository.findByName(newName).isPresent()) {
            throw new ConstraintViolationException("DbSystem with name already exists");
        }

        DbSystem newSystem = oldSystem.get();
        newSystem.setName(newName);

        return repository.save(newSystem);
    }

    /**
     * Delete system of the given name
     *
     * @param systemName name of system to delete
     */
    @Transactional
    public Iterable<Long> deleteSystem(String systemName) {
        return repository.deleteByName(systemName);
    }

    public void addModuleToSystem(DbSystem system, DbModule module) {
        system.getModulesRel().add(module);
        repository.save(system);
    }

}
