package com.example.backend.api.auth.model;

import com.example.backend.common.handler.RoleEnum;
import com.example.backend.common.handler.RoleEnumTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.type.MappedTypes;

@Getter
@AllArgsConstructor
public enum Role{
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MEMBER("ROLE_MEMBER");

    private String role;
}