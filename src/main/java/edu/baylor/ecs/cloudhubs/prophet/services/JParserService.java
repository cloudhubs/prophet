package edu.baylor.ecs.cloudhubs.prophet.services;


import edu.baylor.ecs.cloudhubs.prophetdto.systemcontext.SystemContext;
import edu.baylor.ecs.cloudhubs.prophetutils.ProphetUtilsFacade;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class JParserService {

    /**
     * Singleton
     */
//    private static SystemContextParser systemContextParser;
//
//    public JParserService() {
//        assert SystemContextParser.JPARSER_VERSION == 4; // Validate that the correct JParser version is being used,
//                                                         // or else prophet-utils will cause this to crash
//        systemContextParser = SystemContextParser.getInstance();
//    }

    /**
     * Supply a path to a directory and retreive System Context
     *
     * @param path Path to directory
     * @return a SystemContext object
     */
//    public SystemContext getSystemContextFromPath(String path) {
//        //TODO: Validate path before calling method. Or do this validation in prophet-utils
//        return systemContextParser.createSystemContextFromPathViaAnalysisContext(path);
//    }

    /**
     * Supply a path to a file and get a System Context from it
     *
     * @param path Path to a file
     * @return a SystemContext object
     */
    public SystemContext getSystemContextFromFile(String path) {
        return ProphetUtilsFacade.getEntityContext(path, new String[0]);

    }

    /**
     * Supply a file and get a System Context from it
     *
     * @param file A file
     * @return A SystemContext object
     */
//    public SystemContext getSystemContextFromFile(File file) {
//        return systemContextParser.createSystemContextFromAnalysisContext(
//                systemContextParser.createAnalysisContextFromFile(file));
//    }

}
