package com.exception;

import javax.persistence.EntityNotFoundException;

public class ReservationNotFoundException extends EntityNotFoundException {
    private static final String DEFAULT_MESSAGE = "This reservation does not exist";

    public ReservationNotFoundException(String errorMessage){
        super(errorMessage);
    }

    public ReservationNotFoundException(){
        super(DEFAULT_MESSAGE);
    }
}
