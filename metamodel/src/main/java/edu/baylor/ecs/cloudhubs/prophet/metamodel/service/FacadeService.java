package edu.baylor.ecs.cloudhubs.prophet.metamodel.service;

import edu.baylor.ecs.cloudhubs.prophet.metamodel.db.System;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.repository.DbClassRepository;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.repository.ModuleRepository;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class FacadeService {

    @Autowired
    private DbClassRepository dbClassRepository;

    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public void createSystem(System system){
        systemRepository.save(system);
    }

    public void deleteAll(){systemRepository.deleteAll(); }

    public List<Object> getAllNodes(){
        return systemRepository.getAllNodes();
    }

    public List<Object> getAllNodesConnected(){
        return systemRepository.getAllConnected();
    }
}
