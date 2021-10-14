package com.example.backend.common.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleEnumTypeHandler <E extends Enum <E>> implements TypeHandler<RoleEnum> {

    private Class <E> type;

    public RoleEnumTypeHandler(Class <E> type) {
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, RoleEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getRole());
    }

    @Override
    public RoleEnum getResult(ResultSet rs, String columnName) throws SQLException {
        String role = rs.getString(columnName);
        return getRoleEnum(role);
    }

    @Override
    public RoleEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return getRoleEnum(code);
    }

    @Override
    public RoleEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String role = cs.getString(columnIndex);
        return getRoleEnum(role);
    }

    private RoleEnum getRoleEnum(String role) {
        try {
            RoleEnum[] enumConstants = (RoleEnum[]) type.getEnumConstants();
            for (RoleEnum roleEnum: enumConstants) {
                if (roleEnum.getRole().equals(role)) {
                    return roleEnum;
                }
            }
            return null;
        } catch (Exception e) {
            throw new TypeException("can't make enum '" + type + "'", e);
        }
    }
}