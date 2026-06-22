package org.aburavov.otus.java.professional.hw09.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.aburavov.otus.java.professional.hw09.core.repository.DataTemplate;
import org.aburavov.otus.java.professional.hw09.core.repository.DataTemplateException;
import org.aburavov.otus.java.professional.hw09.core.repository.executor.DbExecutor;

/** Сохраняет объект в базу, читает объект из базы */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createInstance(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    var list = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            list.add(createInstance(rs));
                        }
                        return list;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        var params = fieldValues(object, entityClassMetaData.getFieldsWithoutId());
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
    }

    @Override
    public void update(Connection connection, T object) {
        var params = fieldValues(object, entityClassMetaData.getFieldsWithoutId());
        params.add(fieldValue(object, entityClassMetaData.getIdField()));
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
    }

    private T createInstance(ResultSet rs) {
        try {
            var fields = entityClassMetaData.getAllFields();
            var args = new Object[fields.size()];
            for (var i = 0; i < fields.size(); i++) {
                args[i] = rs.getObject(fields.get(i).getName());
            }
            return entityClassMetaData.getConstructor().newInstance(args);
        } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> fieldValues(T object, List<Field> fields) {
        var values = new ArrayList<Object>(fields.size());
        for (var field : fields) {
            values.add(fieldValue(object, field));
        }
        return values;
    }

    private Object fieldValue(T object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }
}
