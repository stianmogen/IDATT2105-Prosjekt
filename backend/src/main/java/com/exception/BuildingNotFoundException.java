package com.exception;

import javax.persistence.EntityNotFoundException;

public class BuildingNotFoundException extends EntityNotFoundException {
      private static final String DEFAULT_MESSAGE = "This building does not exist";

      public BuildingNotFoundException(String errorMessage){
            super(errorMessage);
      }

      public BuildingNotFoundException(){
            super(DEFAULT_MESSAGE);
      }
}
