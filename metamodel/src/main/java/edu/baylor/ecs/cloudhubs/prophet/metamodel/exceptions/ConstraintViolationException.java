package edu.baylor.ecs.cloudhubs.prophet.metamodel.exceptions;

public class ConstraintViolationException extends RuntimeException {

    public ConstraintViolationException(String message) {
        super(message);
    }
}
