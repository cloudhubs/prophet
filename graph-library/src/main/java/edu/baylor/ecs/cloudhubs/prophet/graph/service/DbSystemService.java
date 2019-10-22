package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbSystemRepository;
import org.springframework.stereotype.Service;

/**
 * DbSystem Service
 */
@Service
public class DbSystemService {

    private final DbSystemRepository repository;

    public DbSystemService(DbSystemRepository repository) {
        this.repository = repository;
    }

    /**
     * Get all systems
     * @return List of Db Systems
     */
    public Iterable<DbSystem> getAllSystems() {
        return repository.findAll();
    }

    /**
     * Retrieves the System of the name provided
     * @param name Name of system to return
     * @return Db System matching given name
     */
    public DbSystem getSystem(String name) {
        return repository.findByName(name).orElseThrow(() -> new EntityNotFoundException("System with name not found"));
    }

    /**
     * Retrieves the System of the id provided
     * @param id Id of system to return
     * @return Db System matching given id
     */
    public DbSystem getSystem(long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("System with id not found"));
    }

    /**
     * Creates a new system with given name
     * @param systemName Name of the system to create
     */
    public DbSystem createSystem(String systemName) {
        DbSystem dbSystem = new DbSystem();
        dbSystem.setName(systemName);

        validateSystem(dbSystem);
        return repository.save(dbSystem);
    }

    /**
     * Change name of system matching given name
     * @param oldName old name of system to update
     * @param newName new name of system to update
     */
    public DbSystem changeName(String oldName, String newName) {
        return repository.setDbSystemNameByName(oldName, newName).orElseThrow(() -> new EntityNotFoundException("System with name not found"));
    }

    /**
     * Change name of system matching given id
     * @param id id of system to update
     * @param newName new name of system to update
     */
    public DbSystem changeName(long id, String newName) {
        return repository.setDbSystemNameById(id, newName).orElseThrow(() -> new EntityNotFoundException("System with id not found"));
    }

    /**
     * Delete system of the given name
     * @param systemName name of system to delete
     */
    public void deleteSystem(String systemName) {
        repository.deleteByName(systemName);
    }

    /**
     * Delete system of the given id
     * @param id id of system to delete
     */
    public void deleteSystem(long id) {
        repository.deleteById(id);
    }

    /**
     * Performs validation of system
     * @param dbSystem system to perform validations on
     */
    private void validateSystem(DbSystem dbSystem) {
    }
}
