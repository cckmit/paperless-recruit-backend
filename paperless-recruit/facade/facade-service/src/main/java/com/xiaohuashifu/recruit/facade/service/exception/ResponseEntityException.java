package com.xiaohuashifu.recruit.facade.service.exception;

import org.springframework.http.ResponseEntity;

/**
 * 描述：ResponseEntity 异常，即抛出异常时附带 ResponseEntity
 *
 * @author xhsf
 * @create 2021/1/15 19:55
 */
public class ResponseEntityException extends RuntimeException {

    private final ResponseEntity<?> responseEntity;

    public <T> ResponseEntityException(ResponseEntity<T> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity<?> getResponseEntity() {
        return responseEntity;
    }
}
