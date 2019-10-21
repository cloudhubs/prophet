package edu.baylor.ecs.cloudhubs.prophet.service;

import org.springframework.stereotype.Service;

@Service
public class DbSystemService {

    public void createSystem(String systemName){

    }

    public void changeName(String oldName, String newName){

    }

    public void deleteSystem(String systemName){

    }

    /**
     * Future: Recursively delete all "direct" relationships of module
     * (module's classes, class's variables, etc.)
     * @param moduleName
     */
    public void deleteModuleRelRec(String moduleName){

    }

    public void createModuleRel(String moduleName){

    }



}
