package edu.baylor.ecs.cloudhubs.prophet.metamodel.service;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.*;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.*;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.exceptions.ConstraintViolationException;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PyMetaService {
    @Autowired
    @Lazy
    private DbSystemRepository systemRepository;

    public DbSystem persistPyData(PySystem pySystem) {
        // create system
        DbSystem system = new DbSystem();
        system.setName(pySystem.getName());

        // db module -> py app
        for (PyApp pyApp: pySystem.getApps()) {
            DbModule module = new DbModule();
            module.setName(pyApp.getName());

            // db file -> db module
            setModuleNodes(pyApp.getModules(), module);

            // db file -> db package
            setPackages(pyApp.getPackages(), module);

            system.getModules().add(module);
        }

        return create(system);
    }

    private void setPackages(List<PyPackage> pyPackages, DbModule module){
        for (PyPackage pyPackage : pyPackages) {
            if(pyPackage.getModules().size() > 0) {
                setPackage(pyPackage, module);
            }

            if(pyPackage.getPackages().size() > 0) {
                setPackages(pyPackage.getPackages(), module);
            }
        }
    }

    private void setPackage(PyPackage pyPackage, DbModule module){
        DbModule packageModule = new DbModule();
        packageModule.setName(pyPackage.getName());

        setModuleNodes(pyPackage.getModules(), packageModule);

        module.getModules().add(packageModule);
    }

    private void setModuleNodes(List<PyModule> modules, DbModule module) {
        for (PyModule pyAppModule : modules) {
            DbFile file = new DbFile();
            file.setName(pyAppModule.getName());

            // imports
            for (PyImport anImport : pyAppModule.getImports()) {
                DbImport dbImport = new DbImport();
                dbImport.setName(anImport.getName());
                file.getImports().add(dbImport);
            }

            // Db
            for (PyClass pyClass : pyAppModule.getClasses()) {
                DbClass dbClass = new DbClass();
                dbClass.setName(pyClass.getName());

                // imports
                for (PyImport anImport : pyClass.getImports()) {
                    DbImport dbImport = new DbImport();
                    dbImport.setName(anImport.getName());
                    dbClass.getImports().add(dbImport);
                }

                for (PyClass aClass : pyClass.getClasses()) {
                    DbClass bClass = new DbClass();
                    bClass.setName(aClass.getName());

                    // imports
                    for (PyImport anImport : aClass.getImports()) {
                        DbImport dbImport = new DbImport();
                        dbImport.setName(anImport.getName());
                        bClass.getImports().add(dbImport);
                    }

                    dbClass.getClasses().add(bClass);
                }

                for (PyFunction pyFunction : pyClass.getFunctions()) {
                    DbFunction function = new DbFunction();
                    function.setName(pyFunction.getName());

                    // imports
                    for (PyImport anImport : pyFunction.getImports()) {
                        DbImport dbImport = new DbImport();
                        dbImport.setName(anImport.getName());
                        function.getImports().add(dbImport);
                    }

                    dbClass.getFunctions().add(function);
                }

                file.getClasses().add(dbClass);
            }

            for (PyFunction pyFunction : pyAppModule.getFunctions()) {
                DbFunction function = new DbFunction();
                function.setName(pyFunction.getName());

                // imports
                for (PyImport anImport : pyFunction.getImports()) {
                    DbImport dbImport = new DbImport();
                    dbImport.setName(anImport.getName());
                    function.getImports().add(dbImport);
                }

                for (PyClass aClass : pyFunction.getClasses()) {
                    DbClass bClass = new DbClass();
                    bClass.setName(aClass.getName());

                    // imports
                    for (PyImport anImport : aClass.getImports()) {
                        DbImport dbImport = new DbImport();
                        dbImport.setName(anImport.getName());
                        bClass.getImports().add(dbImport);
                    }

                    function.getClasses().add(bClass);
                }

                for (PyFunction aFunction : pyFunction.getFunctions()) {
                    DbFunction bFunction = new DbFunction();
                    bFunction.setName(aFunction.getName());

                    // imports
                    for (PyImport anImport : aFunction.getImports()) {
                        DbImport dbImport = new DbImport();
                        dbImport.setName(anImport.getName());
                        bFunction.getImports().add(dbImport);
                    }

                    function.getFunctions().add(bFunction);
                }

                file.getFunctions().add(function);
            }

            module.getFiles().add(file);
        }
    }

    private DbSystem create(DbSystem system) {
//        if (system.getName() == null) {
//            throw new ConstraintViolationException("DbSystem cannot be null");
//        }
//
//        // check if no entity exists with name
//        Optional<DbSystem> s = systemRepository.findByName(system.getName());
//        if (s.isPresent()) {
//            throw new ConstraintViolationException("DbSystem with name already exists");
//        }

        return systemRepository.save(system);
    }
}
