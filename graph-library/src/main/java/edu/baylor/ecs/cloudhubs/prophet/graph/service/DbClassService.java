package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.ConstraintViolationException;
//import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.graph.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbModule;
import edu.baylor.ecs.cloudhubs.prophet.graph.model.relationship.HasAClassRel;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.ClassRelRepository;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbClassRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DbClassService {
    private final DbClassRepository repository;
    private final DbModuleService moduleService;
    private final ClassRelRepository classRelRepository;

    public DbClassService(DbClassRepository repository, DbModuleService moduleService, ClassRelRepository classRelRepository) {
        this.repository = repository;
        this.moduleService = moduleService;
        this.classRelRepository = classRelRepository;
    }

    public DbClass createByName(String systemName, String moduleName, String className) {
        // get system
        DbModule module = moduleService.getModule(systemName, moduleName);

        // check if class already exists exists
        if (module.getClasses().stream().anyMatch(clazz -> clazz.getName().equals(className))) {
            throw new ConstraintViolationException("Module has class with same name");
        }

        // create module
        DbClass dbClass = new DbClass();
        dbClass.setName(className);

        HasAClassRel classRel = new HasAClassRel();
        classRel.setModule(module);
        classRel.setClazz(dbClass);
        classRel.setSystem(module.getSystemRel().getSystem());
        dbClass.setModuleRel(classRel);

        moduleService.addClassToModule(module, dbClass);

        // save relationship and return the module
        return classRelRepository.save(classRel).getClazz();
    }

    public Iterable<DbClass> getAll() {
        return repository.findAll();
    }

    public DbClass getClass(String systemName, String moduleName, String name) {
        DbModule module = moduleService.getModule(systemName, moduleName);
        return module.getClasses().stream().filter(dbClass -> dbClass.getName().equals(name)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("System-Module-Class pair not found"));
    }

    public DbClass changeName(String systemName, String moduleName, String oldName, String newName) {
        // get system
        // will throw an error if module does not exist
        DbModule module = moduleService.getModule(systemName, moduleName);
        DbClass dbClass = findDbClassFromModule(module.getClasses(), oldName);

        if (dbClass == null) {
            throw new EntityNotFoundException("Relationship among entities not found");
        } else {
            dbClass.setName(newName);
        }

        // save relationship and return the class
        return repository.save(dbClass);
    }

    public void deleteClass(String systemName, String moduleName, String className) {
        repository.delete(getClass(systemName, moduleName, className));
    }

    private DbClass findDbClassFromModule(Set<DbClass> dbClasses, String className) {
        for (DbClass c : dbClasses) {
            if (c.getName().equals(className))
                return c;
        }
        return null;
    }
}
