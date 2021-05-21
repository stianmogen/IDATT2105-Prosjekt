package com.exception;

public class RoleNotFoundException extends EntityNotFoundException{
      private static final String DEFAULT_MESSAGE = "Could not find this role";

      public RoleNotFoundException(String errorMessage){
            super(errorMessage);
      }

      public RoleNotFoundException(){
            super(DEFAULT_MESSAGE);
      }
}
