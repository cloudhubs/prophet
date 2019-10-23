/**
 * Copyright 2019, Cloud Innovation Labs, All rights reserved
 * Version: 1.0
 */

package edu.baylor.ecs.cloudhubs.database;

import edu.baylor.ecs.cloudhubs.database.model.DBSystem;
import edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.parser.JPSystem;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.schema.ConstraintDefinition;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;

import javax.ejb.Stateless;

@Stateless
public class SystemRepository extends EmbeddedNeo4j{
//
//    @Inject
//    private EmbeddedNeo4j embeddedNeo4j;

//    private GraphDatabaseService graphDb;
    private Relationship relationship;
    private static final String MODULENAME_KEY = "modulename";
    private static Index<Node> nodeIndex;

//    public Node getNodeById(String id){
//        try ( Transaction tx = graphDb.beginTx() )
//        {
//            try ( ResourceIterator<Node> users =
//                          graphDb.findNodes( label, "name", nameToFind ) )
//            {
//                ArrayList<Node> userNodes = new ArrayList<>();
//                while ( users.hasNext() )
//                {
//                    userNodes.add( users.next() );
//                }
//
//                for ( Node node : userNodes )
//                {
//                    DBSystem.out.println(
//                            "The username of user " + idToFind + " is " + node.getProperty( "username" ) );
//                }
//            }
//        }
//
//
//    }


    public void createSystemModules(JPSystem jpSystem){

        //DBSystem index
        IndexDefinition indexDefinition;
        try ( Transaction tx = graphDb.beginTx() )
        {
            Schema schema = graphDb.schema();
            ConstraintDefinition constraintDefinition = schema
                    .constraintFor(Label.label("SystemName"))
                    .assertPropertyIsUnique("systemName")
//                    .indexFor( Label.label( "SystemName" ) )
////                    .on( "systemName" )
                    .create();

//            indexDefinition = schema
////                    .constraintFor(Label.label("SystemName"))
////                    .assertPropertyIsUnique("systemName")
//                    .indexFor( Label.label( "SystemName" ) )
//                    .on( "systemName" )
//                    .create();

            tx.success();



        }




        try(Transaction tx = graphDb.beginTx()){

            //init primitive types


            Node system = graphDb.createNode();
            system.setProperty("systemName", jpSystem.getName());

            System.out.print( system.getProperty( "systemName" ) );
//            Label label = Label.label( "SystemName" );

//            List<Node> nodes = new ArrayList<>();
//            for (JPModule m: jpSystem.getModules()
//                 ) {
//                Node n = graphDb.createNode();
//                n.setProperty("name", m.getName());
//                nodes.add(n);
//                relationship = system.createRelationshipTo( n, RelTypes.HAS_A_MODULE );
//
//            }
            tx.success();
        }

//        Label label = Label.label( "User" );
//        int idToFind = 45;
//        String nameToFind = "user" + idToFind + "@neo4j.org";
//        try ( Transaction tx = graphDb.beginTx();
//              ResourceIterator<Node> users = graphDb.findNodes( label, "username", nameToFind ) )
//        {
//            Node firstUserNode;
//            if ( users.hasNext() )
//            {
//                firstUserNode = users.next();
//            }
//            users.close();
//        }
    }


    public JPSystem getByName(String name){
//        Label label = Label.label( "SystemName" );
        try ( Transaction tx = graphDb.beginTx() )

        {Node node = this.factory.getOrCreate( "systemName", "a" );
//            DBSystem system = new DBSystem(node);
            JPSystem jpSystem = new JPSystem();
            jpSystem.setName((String)node.getProperty("systemName"));
        tx.success();return jpSystem;
//            try ( ResourceIterator<Node> users =
//                          graphDb.findNodes( label, "SystemName", name ) )
//            {
//                ArrayList<Node> userNodes = new ArrayList<>();
//                while ( users.hasNext() )
//                {
//                    userNodes.add( users.next() );
//                }
//
//                for ( Node node : userNodes )
//                {
//                    DBSystem.out.println(
//                            "The username of user " + " is " + node.getProperty( "systemName" ) );
//                }
//            }
        }

    }

    private Node createAndIndexUser( final String username ) {
        Node node = graphDb.createNode();
        node.setProperty(MODULENAME_KEY, username );
        nodeIndex.add( node, MODULENAME_KEY, username );
        return node;
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