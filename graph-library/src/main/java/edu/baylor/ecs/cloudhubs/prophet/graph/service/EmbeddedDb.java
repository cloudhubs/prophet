package edu.baylor.ecs.cloudhubs.prophet.graph.service;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.ogm.session.Neo4jSession;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmbeddedDb {

    private static final File databaseDirectory = new File("target/neo4j-hello-db");
    private GraphDatabaseService graphDb;

    public GraphDatabaseService registerDb() {
        GraphDatabaseSettings.BoltConnector bolt = GraphDatabaseSettings.boltConnector("0");

        graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(databaseDirectory)
                .setConfig(bolt.type, "BOLT")
                .setConfig(bolt.enabled, "true")
                .setConfig(bolt.address, "localhost:7687")
                .newGraphDatabase();

        return graphDb;
    }


    public void shutDown() {
        System.out.println("Shutting down database ...");
        graphDb.shutdown();
    }
}
