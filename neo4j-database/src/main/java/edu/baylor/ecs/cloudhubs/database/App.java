package edu.baylor.ecs.cloudhubs.database;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Hello world!
 *
 */
@ApplicationScoped
public class App 
{

    private EmbeddedNeo4j embeddedNeo4j;

    @Inject
    public App(EmbeddedNeo4j embeddedNeo4j){
        this.embeddedNeo4j = embeddedNeo4j;
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
