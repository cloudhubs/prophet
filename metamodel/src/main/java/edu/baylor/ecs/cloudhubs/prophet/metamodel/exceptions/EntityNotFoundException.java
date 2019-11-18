package edu.baylor.ecs.cloudhubs.prophet.metamodel.exceptions;

/**
 * Generic Exception thrown when entity/model is read but not found
 */
public class EntityNotFoundException extends org.neo4j.cypher.EntityNotFoundException {
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String message) {
        super(message, new IllegalArgumentException(message));
    }
}
