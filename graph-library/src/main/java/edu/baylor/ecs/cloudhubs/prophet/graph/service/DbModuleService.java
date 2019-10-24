package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import org.springframework.stereotype.Service;

@Service
public class DbModuleService {

//    @Autowired
//    private DbModuleRepository repository;
//
//    @Autowired
//    private DbSystemService systemService;
//
//    /**
//     * Get all modules
//     *
//     * @return List of Db Systems
//     */
//    public Iterable<DbModule> getAllModules() {
//        return repository.findAll();
//    }
//
//    /**
//     * Retrieves the Module of the name provided
//     *
//     * @param name Name of module to return
//     * @return Db Module matching given name
//     */
//    public DbModule getModule(String name) {
//        return repository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Module with name not found"));
//    }
//
//    /**
//     * Retrieves the Module of the id provided
//     *
//     * @param id Id of module to return
//     * @return Db Module matching given id
//     */
//    public DbModule getModule(long id) {
//        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Module with id not found"));
//    }
//
//    /**
//     * Creates a new module with given name
//     *
//     * @param moduleName Name of the module to createByName
//     */
//    public DbModule createModule(String moduleName) {
//        DbModule module = new DbModule();
//        module.setName(moduleName);
//        validateModule(module);
//
//        return repository.save(module);
//    }
//
//    public DbModule createModule(String systemName, String moduleName) {
//        DbSystem system = systemService.getSystem(systemName);
//        DbModule module = new DbModule();
//        module.setName(moduleName);
//
//        validateModule(module);
//        module = repository.save(module);
//
//        system.getModules().add(module);
//        //module.
//
//        return module;
//    }
//
//
//    public void changeName(String oldName, String newName) {
//        repository.setDbModuleNameByName(oldName, newName);
//    }
//
//    public void changeName(long id, String newName) {
//        repository.setDbModuleNameById(id, newName);
//    }
//
//    public void deleteModule(String moduleName) {
//        repository.deleteByName(moduleName);
//    }
//
//    public void deleteModule(long id) {
//        repository.deleteById(id);
//    }
//
//    private void validateModule(DbModule module) {
//    }
}
