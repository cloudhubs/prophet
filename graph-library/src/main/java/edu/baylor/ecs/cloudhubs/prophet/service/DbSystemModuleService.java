package edu.baylor.ecs.cloudhubs.prophet.service;

import edu.baylor.ecs.cloudhubs.prophet.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.model.relationship.HasAModuleRel;
import edu.baylor.ecs.cloudhubs.prophet.repository.DbSystemModuleRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DbSystemModuleService {
    private final DbSystemModuleRepository repository;

    public DbSystemModuleService(DbSystemModuleRepository repository) {
        this.repository = repository;
    }

    public List<DbModule> getModulesForSystem(String systemName) {
        List<HasAModuleRel> systemModules = repository.findAllDbSystemModuleBySystemName(systemName);
        return systemModules.stream().map(HasAModuleRel::getModule).collect(Collectors.toList());
    }

    public DbSystem getSystemForModule(String moduleName) {
        return repository.findDbSystemModuleByModuleName(moduleName).getSystem();
    }
}
