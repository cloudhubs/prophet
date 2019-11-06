package edu.baylor.ecs.cloudhubs.prophet.application.services;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.DbClass;
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

        DbClass dbClass = new DbClass();
        dbClass.setName("ClassC");
        dbClassRepository.save(dbClass);
    }

}
