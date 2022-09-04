package com.example.backend.config.database;
import com.querydsl.core.QueryException;
import com.querydsl.core.types.Path;
import com.querydsl.core.util.ReflectionUtils;
import com.querydsl.sql.ColumnMetadata;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.dml.Mapper;
import com.querydsl.sql.types.Null;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityMapper implements Mapper<Object> {
    public static final EntityMapper DEFAULT = new EntityMapper(false);

    public static final EntityMapper WITH_NULL_BINDINGS = new EntityMapper(true);

    private final boolean withNullBindings;

    public EntityMapper(boolean withNullBindings) {
        this.withNullBindings = withNullBindings;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Map<Path<?>, Object> createMap(RelationalPath<?> path, Object object) {
        try {
            Map<String, Path<?>> columnToPath = new HashMap<>();
            for (Path<?> column : path.getColumns()) {
                columnToPath.put(ColumnMetadata.getName(column), column);
            }
            Map<Path<?>, Object> values = new HashMap<>();
            for (Field field : ReflectionUtils.getFields(object.getClass())) {
                putByEmbedded(object, columnToPath, values, field);
                putByColumn(object, columnToPath, values, field);
                putByJoinColumn(object, columnToPath, values, field);
            }
            return values;
        } catch (IllegalAccessException e) {
            throw new QueryException(e);
        }
    }

    // @Embedded 로 지정된 오브젝트들의 필드들을 테이블 컬럼으로 매핑한다.
    void putByEmbedded(Object object, Map<String, Path<?>> columnToPath, Map<Path<?>, Object> values, Field field) throws IllegalAccessException {
        Embedded ann = field.getAnnotation(Embedded.class);
        if (ann != null) {
            field.setAccessible(true);
            Object embeddedObject = field.get(object);
            if (embeddedObject != null) {
                for (Field embeddedField : ReflectionUtils.getFields(embeddedObject.getClass())) {
                    putByColumn(embeddedObject, columnToPath, values, embeddedField);
                }
            }
        }
    }

    // @Column (name) 값을 매핑할 수 있도록 한다.
    void putByColumn(Object object, Map<String, Path<?>> columnToPath, Map<Path<?>, Object> values, Field field) throws IllegalAccessException {
        Column ann = field.getAnnotation(Column.class);
        if (ann != null) {
            field.setAccessible(true);
            Object propertyValue = field.get(object);
            String columnName = ann.name();
            if (propertyValue != null) {
                if (columnToPath.containsKey(columnName)) {
                    values.put(columnToPath.get(columnName), propertyValue);
                }
            } else if (withNullBindings) {
                values.put(columnToPath.get(columnName), Null.DEFAULT);
            }
        }
    }

    // @JoinColumn 이 있을 경우 참조하는 오브젝트의 Key를 사용할 수 있도록 지정한다.
    void putByJoinColumn(Object object, Map<String, Path<?>> columnToPath, Map<Path<?>, Object> values, Field field) throws IllegalAccessException {
        JoinColumn ann = field.getAnnotation(JoinColumn.class);
        if (ann != null) {
            field.setAccessible(true);
            Object joinObject = field.get(object);
            String columnName = ann.name();
            String joinColumnName = ann.referencedColumnName();
            if (joinObject != null) {
                if (columnToPath.containsKey(columnName)) {
                    Object joinColumnValue = getJoinColumnValue(joinObject, joinColumnName);
                    if(joinColumnValue != null) {
                        values.put(columnToPath.get(columnName), joinColumnValue);
                    }
                }
            } else if (withNullBindings) {
                values.put(columnToPath.get(columnName), Null.DEFAULT);
            }
        }
    }

    private Object getJoinColumnValue(Object joinObject, String joinColumnName) throws IllegalAccessException {
        for (Field field : ReflectionUtils.getFields(joinObject.getClass())){
            Column ann = field.getAnnotation(Column.class);
            if(ann != null && ann.name().equals(joinColumnName)) {
                field.setAccessible(true);
                return field.get(joinObject);
            }
        }
        return null;
    }
}