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
        systemService.createByName(data.getGlobal().getProjectName());

        // persist the microservices
        data.getMs().stream().map(ms -> moduleService.createByName(data.getGlobal().getProjectName(), ms.getName()));


    }
}
