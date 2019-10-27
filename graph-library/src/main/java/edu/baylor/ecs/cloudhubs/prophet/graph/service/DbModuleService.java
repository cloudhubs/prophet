package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.relationship.HasAModuleRel;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbModuleRepository;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.ModuleRelRepository;
import org.neo4j.cypher.internal.frontend.v2_3.ast.functions.Has;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class DbModuleService {

    @Autowired
    private DbModuleRepository repository;

    @Autowired
    private ModuleRelRepository moduleRelRepository;

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

    public Set<DbModule> getAllForSystem(String systemName) {
        DbSystem system = systemService.findByName(systemName);
        return system.getModulesRel();
    }

    /**
     * Retrieves the Module of the name provided
     *
     * @param name Name of module to return
     * @return Db Module matching given name
     */
    public DbModule getModule(String systemName, String name) {
      DbSystem system = systemService.findByName(systemName);
      return system.getModulesRel().stream().filter(module -> module.getName().equals(name)).findFirst().get();
    }


    public DbModule createByName(String systemName, String moduleName) {
        // get system
        DbSystem system = systemService.getSystem(systemName);

        // create module
        DbModule module = new DbModule();
        module.setName(moduleName);

        // check if system exists
        if (system.getModulesRel().contains(module)) {
            throw new ConstraintViolationException("System has module with same name");
        }

        HasAModuleRel moduleRel = new HasAModuleRel();
        moduleRel.setSystem(system);
        moduleRel.setModule(module);

        // you need to persist on the side of outgoing too
        // this is probably smth that needs to be fixed
        // TODO: Fix this, in such a way that we dont have to persist it again from system side
        systemService.addModuleToSystem(system, module);

        return moduleRelRepository.save(moduleRel).getModule();
    }


    public void changeName(String systemName, String oldName, String newName) {
        // get system
        DbSystem system = systemService.getSystem(systemName);

        // check if relationship exists
        moduleRelRepository.findBySystemNameAndModuleName(systemName, oldName)
                .orElseThrow(() -> new EntityNotFoundException("System with name not found"));

        // update module's name
        repository.setDbModuleNameByNameAndSystemName(systemName, oldName, newName)
                .orElseThrow(() -> new EntityNotFoundException("Error updating module"));
    }

    public void deleteModule(String systemName, String moduleName) {
        moduleRelRepository.deleteBySystemNameAndModuleName(systemName, moduleName);
    }

}
