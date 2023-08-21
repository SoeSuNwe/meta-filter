package com.meta.filter.assignment;

import com.meta.filter.visitor.ExpressionVisitor;

public class CompoundExpression extends AbstractExpression{
    public CompoundExpression(){
        super();
    }
    public CompoundExpression(Expression leftOperand,Operator operator,Expression rightOperand){
        super(leftOperand,operator,rightOperand);
    }
    @Override
    public<T> T accept(ExpressionVisitor visitor,T data){
        return (T) visitor.visitCompoundExpression(this,data);
    }
}
