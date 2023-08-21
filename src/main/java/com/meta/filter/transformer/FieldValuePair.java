package com.meta.filter.transformer;

public class FieldValuePair<V> {
    private String fieldName;
    private V value;

    public FieldValuePair(String fieldName, V value) {
        this.fieldName = fieldName;
        this.value = value;
    }
    public String getFieldName() {
        return fieldName;
    }

    public V getValue() {
        return value;
    }
}
