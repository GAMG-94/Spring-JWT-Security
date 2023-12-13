package com.spring.security.services.models.dtos;

import lombok.Data;

@Data
public class ResponseDTO {

    private Integer numOfErrors;
    private String message;
}
