package edu.baylor.ecs.cloudhubs.prophet.graph.exceptions;

public class ConstraintViolationException extends RuntimeException {

    public ConstraintViolationException(String message) {
        super(message);
    }
}
