package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.factory.context.AnalysisContextFactory;
import edu.baylor.ecs.ciljssa.factory.directory.DirectoryFactory;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.Module;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.System;
import org.springframework.stereotype.Service;

import java.nio.file.NotDirectoryException;

@Service
public class JParserService extends FacadeService {

    //Todo: call Jparser library and convert data and insert to db via FacadeService

    /**
     * Validates that a directory is valid and then creates a directory graph from it which is used to produce the
     * CIL-JSSA Meta Model / Context graph.
     * @param directoryLocation a string correlating to the root absolute directory path for a project.
     * @return a System object which is the Prophet DB Meta Model.
     */
    public System createSystemFromSourceCodeViaDirectory(String directoryLocation) throws NotDirectoryException {
        // Create DirectoryGraph for AnalysisContext graph
        DirectoryFactory directoryFactory = new DirectoryFactory();
        DirectoryComponent root = (DirectoryComponent) directoryFactory.createDirectoryGraph(directoryLocation);
        if (root == null)
            throw new NotDirectoryException("Input was not a directory root path! DirectoryComponent is null!");
        // Create AnalysisContextFactory and create CIL-JSSA Meta Model
        AnalysisContextFactory factory = new AnalysisContextFactory();
        AnalysisContext context = factory.createAnalysisContextFromDirectoryGraph(root);
        // For each ciljssa::ModuleComponent, create a corresponding prophet::Module
        System system = new System();
        for (ModuleComponent module : context.getModules()) {
            Module mod = new Module();
            mod.setName(module.getInstanceName());
            mod.setSystem(system);
            for (ClassComponent clazz : module.getClasses()) {
                DbClass dbClass = new DbClass();
                dbClass.setName(clazz.getInstanceName());
                mod.addClass(dbClass);
            }
            system.addModule(mod);
        }

        return system;
    }

}
