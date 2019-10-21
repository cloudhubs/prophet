package edu.baylor.ecs.cloudhubs.prophet.exceptions;

public class EntityNotFoundException extends org.neo4j.cypher.EntityNotFoundException {
  public EntityNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
  public EntityNotFoundException(String message) {
    super(message, new IllegalArgumentException(message));
  }
}
