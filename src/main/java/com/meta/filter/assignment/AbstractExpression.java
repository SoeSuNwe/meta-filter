package com.meta.filter.assignment;

import com.meta.filter.visitor.ExpressionVisitor;

public abstract class AbstractExpression implements Expression {
    private Expression leftOperand;
    private Operator operator;
    private Expression rightOperand;

    protected AbstractExpression() {
    }

    protected AbstractExpression(Expression leftOperand, Operator operator, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    @Override
    public String infix() {
        StringBuilder expressionBuilder = new StringBuilder();
        expressionBuilder
                .append("(")
                .append(getLeftOperand().infix())
                .append(" ")
                .append(getOperator().getName())
                .append(" ")
                .append(getRightOperand().infix())
                .append(")");
        return expressionBuilder.toString();
    }

    @Override
    public <T> T accept(ExpressionVisitor visitor, T data) {
        return null;
    }

    public Expression getLeftOperand() {
        return leftOperand;
    }

    public Operator getOperator() {
        return operator;
    }

    public Expression getRightOperand() {
        return rightOperand;
    }

    public void setLeftOperand(Expression leftOperand) {
        this.leftOperand = leftOperand;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setRightOperand(Expression rightOperand) {
        this.rightOperand = rightOperand;
    }
}
