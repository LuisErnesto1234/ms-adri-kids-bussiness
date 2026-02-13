package com.adri.kids.inventory.domain.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable e){
        super(message, e);
    }
}
