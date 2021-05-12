package com.exception;

import javax.persistence.EntityNotFoundException;

public class RoomNotFoundException extends EntityNotFoundException {
      private static final String DEFAULT_MESSAGE = "This room does not exist";

      public RoomNotFoundException(String errorMessage){
            super(errorMessage);
      }

      public RoomNotFoundException(){
            super(DEFAULT_MESSAGE);
      }
}
