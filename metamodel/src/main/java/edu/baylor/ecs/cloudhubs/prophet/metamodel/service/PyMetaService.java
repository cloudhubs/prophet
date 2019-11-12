package edu.baylor.ecs.cloudhubs.prophet.metamodel.service;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.*;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.pyparser.*;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PyMetaService {
    @Autowired
    private DbSystemRepository systemRepository;
    @Autowired
    private DbModuleRepository moduleRepository;
    @Autowired
    private DbClassRepository classRepository;
    @Autowired
    private DbFunctionRepository functionRepository;
    @Autowired
    private DbFileRepository fileRepository;
    @Autowired
    private DbImportRepository importRepository;

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

            moduleRepository.save(module);
            system.getModules().add(module);
        }

        return systemRepository.save(system);
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
        setModuleNodes(pyPackage.getModules(), module);
    }

    private void setModuleNodes(List<PyModule> modules, DbModule module) {
        for (PyModule pyAppModule : modules) {
            DbFile file = new DbFile();
            file.setName(pyAppModule.getName());

            // imports
            for (PyImport anImport : pyAppModule.getImports()) {
                DbImport dbImport = new DbImport();
                dbImport.setName(anImport.getName());
                importRepository.save(dbImport);
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
                    importRepository.save(dbImport);
                    dbClass.getImports().add(dbImport);
                }

                for (PyClass aClass : pyClass.getClasses()) {
                    DbClass bClass = new DbClass();
                    bClass.setName(aClass.getName());

                    // imports
                    for (PyImport anImport : aClass.getImports()) {
                        DbImport dbImport = new DbImport();
                        dbImport.setName(anImport.getName());
                        importRepository.save(dbImport);
                        bClass.getImports().add(dbImport);
                    }

                    classRepository.save(bClass);
                    dbClass.getClasses().add(bClass);
                }

                for (PyFunction pyFunction : pyClass.getFunctions()) {
                    DbFunction function = new DbFunction();
                    function.setName(pyFunction.getName());

                    // imports
                    for (PyImport anImport : pyFunction.getImports()) {
                        DbImport dbImport = new DbImport();
                        dbImport.setName(anImport.getName());
                        importRepository.save(dbImport);
                        function.getImports().add(dbImport);
                    }

                    functionRepository.save(function);
                    dbClass.getFunctions().add(function);
                }

                classRepository.save(dbClass);

                file.getClasses().add(dbClass);
            }

            for (PyFunction pyFunction : pyAppModule.getFunctions()) {
                DbFunction function = new DbFunction();
                function.setName(pyFunction.getName());

                // imports
                for (PyImport anImport : pyFunction.getImports()) {
                    DbImport dbImport = new DbImport();
                    dbImport.setName(anImport.getName());
                    importRepository.save(dbImport);
                    function.getImports().add(dbImport);
                }

                for (PyClass aClass : pyFunction.getClasses()) {
                    DbClass bClass = new DbClass();
                    bClass.setName(aClass.getName());

                    // imports
                    for (PyImport anImport : aClass.getImports()) {
                        DbImport dbImport = new DbImport();
                        dbImport.setName(anImport.getName());
                        importRepository.save(dbImport);
                        bClass.getImports().add(dbImport);
                    }

                    classRepository.save(bClass);
                    function.getClasses().add(bClass);
                }

                for (PyFunction aFunction : pyFunction.getFunctions()) {
                    DbFunction bFunction = new DbFunction();
                    bFunction.setName(aFunction.getName());

                    // imports
                    for (PyImport anImport : aFunction.getImports()) {
                        DbImport dbImport = new DbImport();
                        dbImport.setName(anImport.getName());
                        importRepository.save(dbImport);
                        bFunction.getImports().add(dbImport);
                    }

                    functionRepository.save(bFunction);
                    function.getFunctions().add(bFunction);
                }

                functionRepository.save(function);
                file.getFunctions().add(function);
            }

            fileRepository.save(file);

            module.getFiles().add(file);
        }
    }
}
