package edu.baylor.ecs.cloudhubs.prophet.services;

//import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
//import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
//import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
//import edu.baylor.ecs.ciljssa.context.AnalysisContext;
//import edu.baylor.ecs.ciljssa.factory.context.AnalysisContextFactory;
//import edu.baylor.ecs.ciljssa.factory.directory.DirectoryFactory;
//import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbClass;
//import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.Module;
//import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.System;
import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.SystemContext;
import org.springframework.stereotype.Service;
import systemcontext.SystemContextParser;

import java.io.File;

@Service
public class JParserService {

    /**
     * Singleton
     */
    private static SystemContextParser systemContextParser;

    public JParserService() {
        assert SystemContextParser.JPARSER_VERSION == 4; // Validate that the correct JParser version is being used,
                                                         // or else prophet-utils will cause this to crash
        systemContextParser = SystemContextParser.getInstance();
    }

    /**
     * Supply a path to a directory and retreive System Context
     *
     * @param path Path to directory
     * @return a SystemContext object
     */
    public SystemContext getSystemContextFromPath(String path) {
        //TODO: Validate path before calling method. Or do this validation in prophet-utils
        return systemContextParser.createSystemContextFromPathViaAnalysisContext(path);
    }

    /**
     * Supply a path to a file and get a System Context from it
     *
     * @param path Path to a file
     * @return a SystemContext object
     */
    public SystemContext getSystemContextFromFile(String path) {
        return systemContextParser.createSystemContextFromAnalysisContext(
                systemContextParser.createAnalysisContextFromFile(path));
    }

    /**
     * Supply a file and get a System Context from it
     *
     * @param file A file
     * @return A SystemContext object
     */
    public SystemContext getSystemContextFromFile(File file) {
        return systemContextParser.createSystemContextFromAnalysisContext(
                systemContextParser.createAnalysisContextFromFile(file));
    }

}
