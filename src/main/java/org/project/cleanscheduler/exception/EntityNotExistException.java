package org.project.cleanscheduler.exception;

public class EntityNotExistException extends RuntimeException {
    public EntityNotExistException(String message) {
        super(message);
    }
}
