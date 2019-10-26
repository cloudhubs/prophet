package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DbModuleService {

    @Autowired
    private DbModuleRepository repository;

    @Autowired
    private DbSystemService systemService;

    /**
     * Get all modules
     *
     * @return List of Db Systems
     */
    public Iterable<DbModule> getAll() {
        return repository.findAll();
    }

    /**
     * Retrieves the Module of the name provided
     *
     * @param name Name of module to return
     * @return Db Module matching given name
     */
    public DbModule getModule(String name) {
        return repository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Module with name not found"));
    }

    /**
     * Retrieves the Module of the id provided
     *
     * @param id Id of module to return
     * @return Db Module matching given id
     */
    public DbModule getModule(long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Module with id not found"));
    }

    /**
     * Creates a new module with given name
     *
     * @param moduleName Name of the module to createByName
     */
//    public DbModule createByName(String moduleName) {
//        DbModule module = new DbModule();
//        module.setName(moduleName);
//
//        // check if no entity exists with name
//        Optional<DbModule> m = repository.findByName(module.getName());
//        if(m.isPresent()) {
//            throw new ConstraintViolationException("DbModule with name already exists");
//        }
//
//        return repository.save(module);
//    }

    public DbModule createByName(String systemName, String moduleName) {
        DbModule module = new DbModule();
        module.setName(moduleName);


        // get system
        DbSystem system = systemService.getSystem(systemName);

        // check if no entity exists with name and systemName
        if(system.getModulesRel().contains(module)) {
            throw new ConstraintViolationException("System has module with same name");
        }

//        module = repository.save(module);
//        system.getModulesRel().add(module); //TODO: Update system in database

        return module;
    }


    public void changeName(String oldName, String newName) {
        // check if oldName exists
        Optional<DbModule> old = repository.findByName(oldName);
        if(!old.isPresent()) {
            throw new ConstraintViolationException("DbModule with name does not exist");
        }

        DbModule oldModule = old.get();
        DbSystem moduleSystem = oldModule.getSystemRel().getSystem();

        // check if no system with new name exits
        if(moduleSystem.getModulesRel().contains(oldModule)) {
            throw new ConstraintViolationException("DbModule with name already exists");
        }

        repository.setDbModuleNameByName(oldName, newName);
    }

    public void deleteModule(String moduleName) {
        repository.deleteByName(moduleName);
    }

    public void deleteModule(long id) {
        repository.deleteById(id);
    }

    private void validateModule(DbModule module) {
    }
}
