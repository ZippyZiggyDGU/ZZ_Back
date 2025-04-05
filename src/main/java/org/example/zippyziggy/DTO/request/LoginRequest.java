package org.example.zippyziggy.DTO.request;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String userId;

    private String password;

    private boolean rememberMe;

}
