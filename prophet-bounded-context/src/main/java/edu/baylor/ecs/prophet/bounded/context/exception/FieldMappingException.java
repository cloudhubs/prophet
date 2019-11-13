package edu.baylor.ecs.prophet.bounded.context.exception;

public class FieldMappingException extends RuntimeException{
    public FieldMappingException(){
        super();
    }

    public FieldMappingException(String s){
        super(s);
    }
}
