package com.utils;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Response {
      @NonNull
      private String message;

      Object data;
}

