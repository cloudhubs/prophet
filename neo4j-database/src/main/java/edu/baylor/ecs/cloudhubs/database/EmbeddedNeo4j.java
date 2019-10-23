/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.cloudhubs.database;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.graphdb.index.UniqueFactory;
import org.neo4j.io.fs.FileUtils;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmbeddedNeo4j
{
    private static final File databaseDirectory = new File( "target/neo4j-hello-db" );

    protected String greeting;
    protected UniqueFactory.UniqueNodeFactory factory;

    // tag::vars[]
    protected GraphDatabaseService graphDb;
    Node firstNode;
    Node secondNode;
    Relationship relationship;

    public GraphDatabaseService getGraphDb(){
        return graphDb;
    }
    // end::vars[]

    // tag::createReltype[]
    private enum RelTypes implements RelationshipType
    {
        KNOWS
    }
    // end::createReltype[]

//    public static void main( final String[] args ) throws IOException
//    {
//        EmbeddedNeo4j hello = new EmbeddedNeo4j();
//        hello.createDb();
//        hello.removeData();
//        hello.shutDown();
//    }

    public void createDb()
    {
        try {
            FileUtils.deleteRecursively( databaseDirectory );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // tag::startDb[]
//        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( databaseDirectory );

        GraphDatabaseSettings.BoltConnector bolt = GraphDatabaseSettings.boltConnector( "0" );

        graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder( databaseDirectory )
                .setConfig( bolt.type, "BOLT" )
                .setConfig( bolt.enabled, "true" )
                .setConfig( bolt.address, "localhost:7687" )
                .newGraphDatabase();

        registerShutdownHook( graphDb );
        try ( Transaction tx = graphDb.beginTx() ) {
            factory = new UniqueFactory.UniqueNodeFactory(graphDb, "systems") {
                @Override
                protected void initialize(Node node, Map<String, Object> map) {
                    node.addLabel(Label.label( "DBSystem" ));node.setProperty( "name", map.get( "systemName"));
                }
            };
        }



        // end::startDb[]

//        // tag::transaction[]
//        try ( Transaction tx = graphDb.beginTx() )
//        {
//            // Database operations go here
//            // end::transaction[]
//            // tag::addData[]
//            firstNode = graphDb.createNode();
//            firstNode.setProperty( "message", "Hello, " );
//            secondNode = graphDb.createNode();
//            secondNode.setProperty( "message", "World!" );
//
//            relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
//            relationship.setProperty( "message", "brave Neo4j " );
//            // end::addData[]
//
//            // tag::readData[]
//            DBSystem.out.print( firstNode.getProperty( "message" ) );
//            DBSystem.out.print( relationship.getProperty( "message" ) );
//            DBSystem.out.print( secondNode.getProperty( "message" ) );
//            // end::readData[]
//
//            greeting = ( (String) firstNode.getProperty( "message" ) )
//                    + ( (String) relationship.getProperty( "message" ) )
//                    + ( (String) secondNode.getProperty( "message" ) );
//
//            // tag::transaction[]
//            tx.success();
//        }
        // end::transaction[]
    }

    public void removeData()
    {
        try ( Transaction tx = graphDb.beginTx() )
        {
            // tag::removingData[]
            // let's remove the data
            firstNode.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
            firstNode.delete();
            secondNode.delete();
            // end::removingData[]

            tx.success();
        }
    }

    public void shutDown()
    {
        System.out.println();
        System.out.println( "Shutting down database ..." );
        // tag::shutdownServer[]
        graphDb.shutdown();
        // end::shutdownServer[]
    }

    // tag::shutdownHook[]
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
    // end::shutdownHook[]
}