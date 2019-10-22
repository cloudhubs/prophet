package edu.baylor.ecs.cloudhubs.prophet;

import edu.baylor.ecs.cloudhubs.prophet.model.DbSystem;
import edu.baylor.ecs.cloudhubs.prophet.repository.DbSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class MyService {

    private final ServiceProperties serviceProperties;
    private final DbSystemRepository dbSystemRepository;

    @Autowired
    public MyService(ServiceProperties serviceProperties, DbSystemRepository systemRepository) {
        this.serviceProperties = serviceProperties;
        this.dbSystemRepository = systemRepository;
    }

    @Transactional
    public String message() {
        DbSystem dbSystem = new DbSystem();
        dbSystem.setName("system name");
        dbSystemRepository.save(dbSystem);
        //DbSystem newNode = dbSystemRepository.findByName("system name");
        return null;
    }
}
