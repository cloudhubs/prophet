package edu.baylor.ecs.cloudhubs.prophet.services;

import edu.baylor.ecs.cloudhubs.prophetdto.app.ProphetAppData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProphetDataService {
    @Autowired
    private DbSystemService systemService;

    @Autowired
    private DbModuleService moduleService;

    public void persistProphetData(ProphetAppData data) {
        systemService.createByName(data.getProjectName());

        // persist the microservices
        data.getMicroservices().stream().map(ms -> moduleService.createByName(data.getProjectName(), ms.getName()));


    }
}
