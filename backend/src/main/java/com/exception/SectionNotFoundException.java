package com.exception;

import javax.persistence.EntityNotFoundException;

public class SectionNotFoundException extends EntityNotFoundException {
      private static final String DEFAULT_MESSAGE = "This section does not exist";

      public SectionNotFoundException(String errorMessage){
            super(errorMessage);
      }

      public SectionNotFoundException(){
            super(DEFAULT_MESSAGE);
      }
}
