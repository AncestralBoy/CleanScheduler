package org.project.cleanscheduler.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String errorMessage){
        super(errorMessage);
    }
}
