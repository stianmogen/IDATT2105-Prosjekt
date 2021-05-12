package com.exception;

public class EntityNotAvailableException extends RuntimeException{
      private static final String DEFAULT_MESSAGE = "Entity is not available";

      public EntityNotAvailableException(String errorMessage){
            super(errorMessage);
      }

      public EntityNotAvailableException() {
            super(DEFAULT_MESSAGE);
      }
}
