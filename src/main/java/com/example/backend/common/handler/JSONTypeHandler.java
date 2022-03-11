package com.example.backend.common.handler;

import com.example.backend.util.JacksonParsing;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 3. 8.
 */
public class JSONTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {

        String p = JacksonParsing.toString(parameter);
        ps.setObject(i, p);
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String d = rs.getString(columnName);
        if(d == null) return null;
        return distinguishJSON(d);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String d = rs.getString(columnIndex);
        if(d == null) return null;
        return distinguishJSON(d);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String d = cs.getString(columnIndex);
        if(d == null) return null;
        return distinguishJSON(d);
    }

    private Object distinguishJSON(String json){
        Object result = new JSONTokener(json).nextValue();
        if(result instanceof JSONArray){
            return JacksonParsing.toList(json);
        }else if(result instanceof JSONObject){
            return JacksonParsing.toMap(json);
        }
        return json;
    }
}
