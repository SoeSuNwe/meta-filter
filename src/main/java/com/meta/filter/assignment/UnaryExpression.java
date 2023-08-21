package com.meta.filter.assignment;

import com.meta.filter.visitor.ExpressionVisitor;

public class UnaryExpression extends AbstractExpression{
    public UnaryExpression(){
        super();
    }
    public UnaryExpression(Expression leftOperand,Operator operator,Expression rightOperand){
        super(leftOperand,operator,rightOperand);
    }

    @Override
    public <T> T accept(ExpressionVisitor visitor,T data){
        return (T)visitor.visitUnaryExpression(this,data);
    }
}
