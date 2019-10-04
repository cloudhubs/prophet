package edu.baylor.ecs.prophet.bounded.context.api;

import java.util.List;

public interface DatabseApi {

    //get all context models from the system
    List<String> getSysteContextmModels(String name);

    // persist data to database
    String createBoundedContext(String system, String boundedContext);

}
