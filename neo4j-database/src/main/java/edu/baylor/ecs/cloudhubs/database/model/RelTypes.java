package edu.baylor.ecs.cloudhubs.database.model;

import org.neo4j.graphdb.RelationshipType;

public enum RelTypes implements RelationshipType {
    HAS_A_CLASS, HAS_A_MODULE
}
