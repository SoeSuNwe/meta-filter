package com.meta.filter.assignment;

import com.meta.filter.visitor.ExpressionVisitor;

public class ExpressionValue<V> implements Expression {
    private V value;

    public ExpressionValue(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String infix() {
        StringBuilder infix = new StringBuilder("");
        String result = null;
        if (value != null) {
            if (value instanceof Iterable) {
                Iterable<V> values = (Iterable<V>) value;
                for (V val : values) {
                    infix.append(value.toString()).append(",");
                }
                result = infix.toString() == "" ? "" : infix.substring(0, infix.length() - 1);
            } else {
                infix.append(value);
                result = infix.toString();
            }
        }
        return result;
    }

    @Override
    public <T> T accept(ExpressionVisitor visitor, T data) {
        return (T) visitor.visitExpressionValue(this, data);
    }
}
