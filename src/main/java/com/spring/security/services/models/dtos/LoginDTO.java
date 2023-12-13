package com.spring.security.services.models.dtos;

import lombok.Data;

@Data
public class LoginDTO {

    private String email;
    private String password;
}
