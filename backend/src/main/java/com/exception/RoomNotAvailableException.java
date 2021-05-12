package com.exception;

public class RoomNotAvailableException extends EntityNotAvailableException{
      private static final String DEFAULT_MESSAGE = "This room is not available for booking";

      public RoomNotAvailableException(String errorMessage){
            super(errorMessage);
      }

      public RoomNotAvailableException(){
            super(DEFAULT_MESSAGE);
      }
}
