package com.meta.filter.visitor;

import com.meta.filter.assignment.*;
import com.meta.filter.transformer.FieldValuePair;
import com.meta.filter.transformer.FieldValueTransformer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class InfixExpressionVisitor implements ExpressionVisitor<String> {
    private Map<String, String> fieldMap;
    private Deque<ExpressionField> fieldStack;
    private FieldValueTransformer fieldValueTransformer;

    public InfixExpressionVisitor(Map<String, String> fieldMap, FieldValueTransformer fieldValueTransformer) {
        this.fieldMap = fieldMap;
        this.fieldStack = new ArrayDeque<>();
        this.fieldValueTransformer = fieldValueTransformer;
    }

    @Override
    public String expression(Expression expression) {
        String expressionString = "";
        if (expression != null) {
            expressionString = expression.accept(this, expressionString);
        }
        System.out.println("\n infix expression => " + expressionString + "\n\n ---------------------------------------------------------------");
        return expressionString;
    }

    @Override
    public String visitBinaryExpression(BinaryExpression binaryExpression, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        expressionBuilder.append("(")
                .append(binaryExpression.getLeftOperand().accept(this, ""))
                .append(" ").append(binaryExpression.getOperator().getName()).append(" ")
                .append(binaryExpression.getRightOperand().accept(this, ""))
                .append(")");
        //  System.out.println("visitBinaryExpression=>" + expressionBuilder);
        return expressionBuilder.toString();
    }

    @Override
    public String visitCompoundExpression(CompoundExpression compoundExpression, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        expressionBuilder.append("(")
                .append(compoundExpression.getLeftOperand().accept(this, ""))
                .append(" ").append(compoundExpression.getOperator().getName()).append(" ")
                .append(compoundExpression.getRightOperand().accept(this, ""))
                .append(")");
        // System.out.println("visitCompoundExpression=>" + expressionBuilder);
        return expressionBuilder.toString();
    }

    @Override
    public String visitUnaryExpression(UnaryExpression unaryExpression, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        expressionBuilder.append("(")
                .append(" ").append(unaryExpression.getOperator()).append(" ")
                .append(unaryExpression.getLeftOperand().accept(this, ""))
                .append(")");
        //System.out.println("visitUnaryExpression=>" + expressionBuilder);
        return expressionBuilder.toString();
    }

    @Override
    public String visitExpressionField(ExpressionField field, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        if (fieldMap != null && fieldMap.get(field.infix()) != null) {
            expressionBuilder.append(fieldMap.get(field.infix()));
        } else if (fieldValueTransformer != null && fieldValueTransformer.transformField(field.infix()) != null) {
            expressionBuilder.append(fieldValueTransformer.transformField(field.infix()));
            fieldStack.push(field); //pushing the field for lookup while visiting value.
        } else {
            expressionBuilder.append(field.infix());
        }
        // System.out.println("visitExpressionField=>" + expressionBuilder.toString());

        return expressionBuilder.toString();
    }

    @Override
    public String visitExpressionValue(ExpressionValue<? extends Comparable> value, String data) {
        if (!fieldStack.isEmpty() && fieldValueTransformer != null) {
            ExpressionField field = fieldStack.pop(); // pop the field associated with this value.
            FieldValuePair fieldValuePair = fieldValueTransformer.transformValue(field.infix(), value.getValue());
            if (fieldValuePair != null && fieldValuePair.getValue() != null) {
                value = new ExpressionValue(fieldValuePair.getValue());
            }
        }

        StringBuilder expressionBuilder = new StringBuilder(data);
        expressionBuilder.append(value.getValue());
        // System.out.println("visitExpressionValue=>" + expressionBuilder.toString());
        return expressionBuilder.toString();
    }
}
