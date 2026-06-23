package org.aburavov.otus.java.professional.hw09.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;
    private final String name;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.name = clazz.getSimpleName().toLowerCase();
        this.allFields = List.of(clazz.getDeclaredFields());
        this.allFields.forEach(field -> field.setAccessible(true));
        this.idField = findIdField();
        this.fieldsWithoutId = allFields.stream()
                .filter(field -> !field.equals(idField))
                .collect(Collectors.toList());
        this.constructor = findConstructor();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

    private Field findIdField() {
        var idFields = allFields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .toList();
        if (idFields.isEmpty()) {
            throw new EntityClassMetaDataException("No field annotated with @Id found in " + clazz.getName());
        }
        if (idFields.size() > 1) {
            throw new EntityClassMetaDataException("Exactly one @Id field expected in " + clazz.getName()
                    + ", but found " + idFields.size() + ": " + idFields);
        }
        return idFields.get(0);
    }

    private Constructor<T> findConstructor() {
        var parameterTypes =
                allFields.stream().map(Field::getType).toArray(Class<?>[]::new);
        try {
            return clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException ex) {
            throw new EntityClassMetaDataException("No all-args constructor found in " + clazz.getName()
                    + " with parameters " + Arrays.toString(parameterTypes));
        }
    }
}
