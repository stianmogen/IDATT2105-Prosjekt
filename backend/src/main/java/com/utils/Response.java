package com.utils;

import lombok.*;

import javax.validation.constraints.NotNull;

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

