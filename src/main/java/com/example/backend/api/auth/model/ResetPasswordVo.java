package com.example.backend.api.auth.model;

import lombok.Data;

@Data
public class ResetPasswordVo {

    private int idx;
    private String password;
    private String newPassword;
}
