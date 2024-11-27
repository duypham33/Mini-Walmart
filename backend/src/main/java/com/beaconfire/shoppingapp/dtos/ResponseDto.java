package com.beaconfire.shoppingapp.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private Boolean isSuccess;
    private String message;
    private T data;
    @JsonIgnore
    private HttpStatus httpStatus;

    public static <T> ResponseDto<T> get(T data){
        return ResponseDto.<T>builder()
                .isSuccess(data != null)
                .message(data != null ? null : "Some error happened!")
                .data(data)
                .build();
    }

    public static <T> ResponseDto<T> get(T data, String message){
        final Boolean isSuccess = !(data == null && message != null);
        return ResponseDto.<T>builder()
                .isSuccess(isSuccess)
                .message(message)
                .data(data).httpStatus(isSuccess ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE)
                .build();
    }

    public static <T> ResponseDto<T> get(T data, String message, HttpStatus httpStatus){
        return ResponseDto.<T>builder()
                .isSuccess(!(data == null && message != null))
                .message(message)
                .data(data).httpStatus(httpStatus)
                .build();
    }

    public ResponseEntity<ResponseDto<T>> toResponseEntity(){
        return new ResponseEntity<>(this, httpStatus == null ? HttpStatus.OK : httpStatus);
    }
}
