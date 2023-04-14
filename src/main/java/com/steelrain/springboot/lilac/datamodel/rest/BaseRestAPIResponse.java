package com.steelrain.springboot.lilac.datamodel.rest;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * REST API 의 기본응답 클래스
 */
@Getter
@SuperBuilder
public abstract class BaseRestAPIResponse {
    protected Object requestParameter;
    protected int code;
    protected String message;
    protected String status;
}
