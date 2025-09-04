package com.pratice_project.handlingException;

public class ResouceNotFoundException extends RuntimeException{
    public ResouceNotFoundException(String message) {
        super(message);
    }
}
