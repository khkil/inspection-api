package com.example.backend.util.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role{
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MEMBER("ROLE_MEMBER");

    private String role;
}