package com.example.backend.auth.model;

import com.example.backend.common.handler.RoleEnum;
import com.example.backend.common.handler.RoleEnumTypeHandler;
import lombok.Getter;
import org.apache.ibatis.type.MappedTypes;

@Getter
public enum Role implements RoleEnum {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private String role;

    Role(String role) {
        this.role = role;
    }

    @MappedTypes(Role.class)
    public static class TypeHandler extends RoleEnumTypeHandler<Role> {
        public TypeHandler() {
            super(Role.class);
        }
    }
}