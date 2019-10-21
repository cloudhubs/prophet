package edu.baylor.ecs.cloudhubs.prophet.service;

import edu.baylor.ecs.cloudhubs.prophet.exceptions.EntityNotFoundException;
import edu.baylor.ecs.cloudhubs.prophet.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.repository.DbSystemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbSystemService {

    private final DbSystemRepository dbSystemRepository;

    public DbSystemService(DbSystemRepository dbSystemRepository) {
        this.dbSystemRepository = dbSystemRepository;
    }

    public Iterable<DbSystem> getAllSystems(){
        return dbSystemRepository.findAll();
    }

    public DbSystem getSystem(String name){
        return dbSystemRepository.findByName(name);
    }

    public void createSystem(String systemName){
        DbSystem dbSystem = new DbSystem();
        dbSystem.setName(systemName);

        if(validateSystem(dbSystem)) {
            dbSystemRepository.save(dbSystem);
        }
    }

    public void changeName(String oldName, String newName){
        dbSystemRepository.setDbSystemNameByName(oldName, newName);
    }

    public void deleteSystem(String systemName){
        dbSystemRepository.deleteByName(systemName);
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

    private boolean validateSystem(DbSystem dbSystem) {
        DbSystem db = dbSystemRepository.findByName(dbSystem.getName());
        if(db != null)  return false;

        return true;
    }



}
