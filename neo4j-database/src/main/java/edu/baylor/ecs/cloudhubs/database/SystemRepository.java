package edu.baylor.ecs.cloudhubs.database;

import edu.baylor.ecs.cloudhubs.database.model.RelTypes;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class SystemRepository {

    private EmbeddedNeo4j embeddedNeo4j;
    private GraphDatabaseService graphDb;
    private Relationship relationship;

    @Inject
    public SystemRepository(EmbeddedNeo4j embeddedNeo4j){
        this.embeddedNeo4j = embeddedNeo4j;
        this.graphDb = embeddedNeo4j.getGraphDb();
    }


    public void createSystemModules(String systemName, List<String> modules){
        try(Transaction tx = graphDb.beginTx()){
            Node system = graphDb.createNode();
            system.setProperty("name", systemName);
            List<Node> nodes = new ArrayList<>();
            for (String s: modules
                 ) {
                Node n = graphDb.createNode();
                n.setProperty("name", s);
                nodes.add(n);
                relationship = system.createRelationshipTo( n, RelTypes.HAS_A_MODULE );
            }
        }
    }
}


//        try ( Transaction tx = graphDb.beginTx() ) {
//            // Database operations go here
//            // end::transaction[]
//            // tag::addData[]
//            Node firstNode = graphDb.createNode();
//            firstNode.setProperty( "message", "Hello, " );
//            secondNode = graphDb.createNode();
//            secondNode.setProperty( "message", "World!" );
//
//            relationship = firstNode.createRelationshipTo( secondNode, EmbeddedNeo4j.RelTypes.KNOWS );
//            relationship.setProperty( "message", "brave Neo4j " );
//            // end::addData[]
//
//            // tag::readData[]
//            System.out.print( firstNode.getProperty( "message" ) );
//            System.out.print( relationship.getProperty( "message" ) );
//            System.out.print( secondNode.getProperty( "message" ) );
//            // end::readData[]
//
//            greeting = ( (String) firstNode.getProperty( "message" ) )
//                    + ( (String) relationship.getProperty( "message" ) )
//                    + ( (String) secondNode.getProperty( "message" ) );
//
//            // tag::transaction[]
//            tx.success();
//        }