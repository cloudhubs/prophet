package edu.baylor.ecs.cloudhubs.prophet;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class MyService {

    private final ServiceProperties serviceProperties;
    private final DbSystemRepository dbSystemRepository;

    public MyService(ServiceProperties serviceProperties, DbSystemRepository systemRepository) {
        this.serviceProperties = serviceProperties;
        this.dbSystemRepository = systemRepository;
    }

    @Transactional
    public String message() {
        DbSystem dbSystem = new DbSystem();
        dbSystem.setName("system name");
        dbSystemRepository.save(dbSystem);
        DbSystem newNode = dbSystemRepository.findByName("system name");
        return newNode.getName();
    }
}
