package jpabook.jpashop.api.v1.common.dto.response;

import lombok.Getter;

@Getter
public class ResultResponse<T> {

  private String message;
  private final T data;

  public ResultResponse(T data) {
    this.data = data;
  }

  public ResultResponse<T> registerMessage(String message) {
    this.message = message;

    return this;
  }

  public static <T> ResultResponse<T> of(T data) {
    return new ResultResponse<>(data);
  }

}

