package edu.baylor.ecs.cloudhubs.prophet.application.services;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbClass;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.System;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.Module;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.repository.DbClassRepository;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.repository.ModuleRepository;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    @Autowired
    private DbClassRepository dbClassRepository;

    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public void insertData(){

        System system = new System("SystemA");

        Module module = new Module("ModuleA");

        DbClass dbClass = new DbClass();
        dbClass.setName("ClassC");

        module.addClass(dbClass);
        system.addModule(module);
        systemRepository.save(system);

//        dbClassRepository.save(dbClass);
    }

}
