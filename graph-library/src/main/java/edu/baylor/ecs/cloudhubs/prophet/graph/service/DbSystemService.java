package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * DbSystem Service
 */
@Service
public class DbSystemService {

    @Autowired
    private DbSystemRepository repository;

    public void createByName(String name) {
        if(name == null) {
            throw new ConstraintViolationException("DbSystem cannot be null");
        }

        DbSystem dbSystem = new DbSystem();
        dbSystem.setName(name);

        // check if no entity exists with name
        Optional<DbSystem> s = repository.findByName(dbSystem.getName());
        if(s.isPresent()) {
            throw new ConstraintViolationException("DbSystem with name already exists");
        }
        repository.save(dbSystem);
    }

    public Optional<DbSystem> findByName(String name) {
        return repository.findByName(name);
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
//
//    /**
//     * Retrieves the System of the id provided
//     * @param id Id of system to return
//     * @return Db System matching given id
//     */
//    public DbSystem getSystem(long id) {
//        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("System with id not found"));
//    }
//
//    /**
//     * Creates a new system with given name
//     * @param systemName Name of the system to createByName
//     */
//    public DbSystem createSystem(String systemName) {
//        DbSystem dbSystem = new DbSystem();
//        dbSystem.setName(systemName);
//
//        validateSystem(dbSystem);
//        return repository.save(dbSystem);
//    }
//

    /**
     * Change name of system matching given name
     *
     * @param oldName old name of system to update
     * @param newName new name of system to update
     */
    public DbSystem changeName(String oldName, String newName) {
        if(oldName == null || newName == null) {
            throw new ConstraintViolationException("DbSystem cannot be null");
        }

        // check if oldName exists
        if(!repository.findByName(oldName).isPresent()) {
            throw new ConstraintViolationException("DbSystem with name does not exist");
        }

        // check if no system with new name exits
        if(repository.findByName(newName).isPresent()) {
            throw new ConstraintViolationException("DbSystem with name already exists");
        }

        return repository.setDbSystemNameByName(oldName, newName).get();
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

}
