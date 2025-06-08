package com.lxs.note_backend.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String message;

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }
    public LoginResponse(String token) {
        this.token = token;
    }

}
