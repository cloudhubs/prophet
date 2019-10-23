package edu.baylor.ecs.cloudhubs.prophet.graph;

import edu.baylor.ecs.cloudhubs.prophet.graph.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.graph.repository.DbSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class MyService {

    private final ServiceProperties serviceProperties;

    @Autowired
    public MyService(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    @Transactional
    public String message() {
        DbSystem dbSystem = new DbSystem();
        dbSystem.setName("system name");
//        dbSystemRepository.save(dbSystem);
        //DbSystem newNode = dbSystemRepository.findByName("system name");
        return "a";
    }
}
