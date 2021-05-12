package com.exception;

public class SectionNotAvailableException extends EntityNotAvailableException{
      private static final String DEFAULT_MESSAGE = "This section is not available for booking";

      public SectionNotAvailableException(String errorMessage){
            super(errorMessage);
      }

      public SectionNotAvailableException(){
            super(DEFAULT_MESSAGE);
      }
}
