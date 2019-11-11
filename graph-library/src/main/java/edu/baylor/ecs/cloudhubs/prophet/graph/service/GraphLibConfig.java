package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configurable
@Configuration
//@ComponentScan({"edu.baylor.ecs.cloudhubs.prophet.graph.service", "edu.baylor.ecs.cloudhubs.prophet.metamodel" +
//        ".service"})
//@EntityScan("edu.baylor.ecs.cloudhubs.prophet.metamodel.db")
//@EnableNeo4jRepositories({"edu.baylor.ecs.cloudhubs.prophet.metamodel.repository","edu.baylor.ecs.cloudhubs.prophet" +
//        ".graph" +
//        ".repository"})
public class GraphLibConfig {
}
