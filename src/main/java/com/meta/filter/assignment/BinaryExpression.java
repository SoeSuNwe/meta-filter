package com.meta.filter.assignment;

import com.meta.filter.visitor.ExpressionVisitor;

public class BinaryExpression extends AbstractExpression {

    public BinaryExpression() {
        super();
    }

    public BinaryExpression(Expression leftOperand, Operator operator, Expression rightOperand) {
        super(leftOperand, operator, rightOperand);
    }
    @Override
    public <T> T accept(ExpressionVisitor visitor, T data) {
        return (T) visitor.visitBinaryExpression(this, data);
    }
}
