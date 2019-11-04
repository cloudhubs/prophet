package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.internal.SessionFactory;
import org.neo4j.driver.internal.SessionFactoryImpl;
import org.neo4j.driver.internal.async.NetworkSession;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;

import java.util.concurrent.CompletionStage;

//Attempt to implement session
public class Neo4jSessionFactory {

    private final static Configuration configuration = new Configuration.Builder()
            .uri("bolt://localhost")
    .credentials("neo4j", "password")
    .build();


//    private final static SessionFactory sessionFactory = new SessionFactory(configuration, "school.domain") {
//        @Override
//        public NetworkSession newInstance(SessionConfig sessionConfig) {
//            return null;
//        }
//
//        @Override
//        public CompletionStage<Void> verifyConnectivity() {
//            return null;
//        }
//
//        @Override
//        public CompletionStage<Void> close() {
//            return null;
//        }
//    };
//    private static Neo4jSessionFactory factory = new Neo4jSessionFactory();
//
//    public static Neo4jSessionFactory getInstance() {
//        return factory;
//    }
//
//    // prevent external instantiation
//    private Neo4jSessionFactory() {
//    }
//
//    public Session getNeo4jSession() {
//        return sessionFactory.openSession();
//    }
}
