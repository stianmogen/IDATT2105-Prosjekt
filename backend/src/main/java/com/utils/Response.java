package com.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {
      private String message;

      public Response(String message){
            this.message = message;
      }

      Object data;
}

