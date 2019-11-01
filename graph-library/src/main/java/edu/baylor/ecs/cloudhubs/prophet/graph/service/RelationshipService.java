//package edu.baylor.ecs.cloudhubs.prophet.graph.service;
//
//import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
//import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
//import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
//import edu.baylor.ecs.cloudhubs.prophet.graph.model.relationship.HasAModuleRel;
//import edu.baylor.ecs.cloudhubs.prophet.graph.repository.*;
//import org.apache.commons.lang3.NotImplementedException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class RelationshipService {
//
//    private final DbSystemRepository systemRepository;
//    private final DbModuleRepository moduleRepository;
//    private final DbClassRepository classRepository;
//    private final ModuleRelRepository moduleRelRepository;
//    private final ClassRelRepository classRelRepository;
//
//    public RelationshipService(DbSystemRepository systemRepository,
//                          DbModuleRepository moduleRepository,
//                          DbClassRepository classRepository,
//                          ModuleRelRepository moduleRelRepository,
//                          ClassRelRepository classRelRepository) {
//        this.systemRepository = systemRepository;
//        this.moduleRepository = moduleRepository;
//        this.classRepository = classRepository;
//        this.moduleRelRepository = moduleRelRepository;
//        this.classRelRepository = classRelRepository;
//    }
//
//    public HasAModuleRel createModuleRel(DbSystem system, DbModule module) {
//        HasAModuleRel rel = new HasAModuleRel();
//        rel.setModule(module);
//        rel.setSystem(system);
//
//        return moduleRelRepository.save(rel);
//    }
//
//    public void deleteSystemRelRec(String systemName) {
//        DbSystem system = systemRepository.findByName(systemName).orElseThrow(() -> new EntityNotFoundException("System with name not found"));
//        system.getModulesRel().forEach(module -> deleteModuleRelRec(module.getName()));
//        systemRepository.deleteByName(system.getName());
//    }
//
//    /**
//     * Future: Recursively delete all "direct" relationship of module
//     * (module's classes, class's variables, etc.)
//     *
//     * @param moduleName
//     */
//    public void deleteModuleRelRec(String moduleName) {
//        DbModule module = moduleRepository.findByName(moduleName).orElseThrow(() -> new EntityNotFoundException("Module with name not found"));
//        module.getClasses().forEach(clazz -> deleteClassRelRec(clazz.getName()));
//        moduleRepository.deleteByName(module.getName());
//    }
//
//    public void deleteClassRelRec(String className) {
//        classRepository.deleteByName(className);
//    }
//
//    public List<DbModule> getModulesForSystem(String systemName) {
//        List<HasAModuleRel> systemModules = moduleRelRepository.findAllDbSystemModuleBySystemName(systemName);
//        return systemModules.stream().map(HasAModuleRel::getModule).collect(Collectors.toList());
//    }
//
//    public DbSystem getSystemForModule(String moduleName) {
//        return moduleRelRepository.findDbSystemModuleByModuleName(moduleName).getSystem();
//    }
//}
