package org.aburavov.otus.java.professional.hw09.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "select " + allColumns() + " from " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return getSelectAllSql() + " where "
                + entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        var fields = entityClassMetaData.getFieldsWithoutId();
        var columns = fields.stream().map(Field::getName).collect(Collectors.joining(", "));
        var placeholders = fields.stream().map(field -> "?").collect(Collectors.joining(", "));
        return "insert into " + entityClassMetaData.getName() + "(" + columns + ") values (" + placeholders + ")";
    }

    @Override
    public String getUpdateSql() {
        var assignments = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(", "));
        return "update " + entityClassMetaData.getName() + " set " + assignments + " where "
                + entityClassMetaData.getIdField().getName() + " = ?";
    }

    private String allColumns() {
        return entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }
}
