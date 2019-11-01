package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.relationship.HasAModuleRel;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbModuleRepository;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.ModuleRelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return system.getModulesRel().stream().filter(module -> module.getName().equals(name)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("System Module pair not found"));
    }

    public DbModule createByName(String systemName, String moduleName) {
        // get system
        DbSystem system = systemService.getSystem(systemName);

        // check if module already exists exists
        if (system.getModulesRel().stream().anyMatch(module -> module.getName().equals(moduleName))) {
            throw new ConstraintViolationException("System has module with same name");
        }

        // create module
        DbModule module = new DbModule();
        module.setName(moduleName);

        HasAModuleRel moduleRel = new HasAModuleRel();
        moduleRel.setSystem(system);
        moduleRel.setModule(module);
        module.setSystemRel(moduleRel);

        // you need to persist on the side of outgoing too
        systemService.addModuleToSystem(system, module);

        // save relationship and return the module
        return moduleRelRepository.save(moduleRel).getModule();
    }


    public DbModule changeName(String systemName, String oldName, String newName) {
        // get system
        // will throw an error if system does not exist
        DbSystem system = systemService.getSystem(systemName);
        DbModule module = findModuleFromSystem(system.getModulesRel(), oldName);

        // check if relationship exists
        if (module == null) {
            throw new EntityNotFoundException("Relationship among entities not found");
        } else {
            module.setName(newName);
        }

        // save relationship and return the module
        return repository.save(module);
    }

    public void deleteModule(String systemName, String moduleName) {
        repository.delete(getModule(systemName, moduleName));
    }

    public void addClassToModule(DbModule module, DbClass clazz) {
        module.getClasses().add(clazz);
        repository.save(module);
    }

    private DbModule findModuleFromSystem(Set<DbModule> modules, String moduleName) {
        for (DbModule m : modules) {
            if (m.getName().equals(moduleName))
                return m;
        }
        return null;
    }
}
