package com.meta.filter.assignment;

import com.meta.filter.visitor.ExpressionVisitor;

public interface Expression {
    public String infix();
    <T> T accept(ExpressionVisitor visitor, T data);
}
