package edu.baylor.ecs.cloudhubs.database.model;

import org.neo4j.graphdb.Node;

public class System {

    static final String NAME = "SYSTEMNAME";
    static final String DESCRIPTION = "System description";

    private final Node underlyingNode;

    public System(Node personNode)
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

    public String getDescription(){
        return (String)underlyingNode.getProperty(DESCRIPTION);
    }

    // end::delegate-to-the-node[]

    // tag::override[]
    @Override
    public int hashCode()
    {
        return underlyingNode.hashCode();
    }

    @Override
    public boolean equals( Object o )
    {
        return o instanceof System &&
                underlyingNode.equals( ( (System)o ).getUnderlyingNode() );
    }

    @Override
    public String toString()
    {
        return "System[" + getName() + "]";
    }
}
