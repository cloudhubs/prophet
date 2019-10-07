package edu.baylor.ecs.cloudhubs.database.model;

import org.neo4j.graphdb.Node;

public class Class {

    static final String NAME = "NAME";

    private final Node underlyingNode;

    Class( Node personNode )
    {
        this.underlyingNode = personNode;
    }

    protected Node getUnderlyingNode()
    {
        return underlyingNode;
    }

    // end::the-node[]

    // tag::delegate-to-the-node[]
    public String getName()
    {
        return (String)underlyingNode.getProperty( NAME );
    }
    // end::delegate-to-the-node[]

    // tag::override[]
    @Override
    public int hashCode()
    {
        return underlyingNode.hashCode();
    }

    @Override
    public boolean equals( Object o ) {
        return o instanceof Class && underlyingNode.equals( ( (Class)o ).getUnderlyingNode() );
    }

    @Override
    public String toString()
    {
        return "Class[" + getName() + "]";
    }
}
