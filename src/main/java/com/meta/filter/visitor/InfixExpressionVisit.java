package com.meta.filter.visitor;

import com.meta.filter.assignment.*;
import com.meta.filter.transformer.FieldValuePair;
import com.meta.filter.transformer.FieldValueTransformer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class InfixExpressionVisit implements ExpressionVisitor<String> {
    private final Deque<Operator> operatorStack;
    private final Map<String, String> fieldMap;
    private final Deque<ExpressionField> fieldStack;
    private final FieldValueTransformer fieldValueTransformer;

    public InfixExpressionVisit(Map<String, String> fieldMap, FieldValueTransformer fieldValueTransformer) {
        this.operatorStack = new ArrayDeque<>();
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
        System.out.println("\n INFIX expression => " + expressionString + "\n\n ---------------------------------------------------------------");
        return expressionString;
    }

    @Override
    public String visitBinaryExpression(BinaryExpression binaryExpression, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        expressionBuilder.append("(")
                .append(binaryExpression.getLeftOperand().accept(this, ""))
                .append(" ").append(resolveOperator(binaryExpression.getOperator())).append(" ");
        operatorStack.push(binaryExpression.getOperator());
        expressionBuilder.append(binaryExpression.getRightOperand().accept(this, ""))
                .append(")");
        System.out.println("visitBinaryExpression=>" + expressionBuilder);
        return expressionBuilder.toString();
    }

    @Override
    public String visitCompoundExpression(CompoundExpression compoundExpression, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        expressionBuilder.append("(")
                .append(compoundExpression.getLeftOperand().accept(this, ""))
                .append(" ").append(resolveOperator(compoundExpression.getOperator())).append(" ")
                .append(compoundExpression.getRightOperand().accept(this, ""))
                .append(")");
        System.out.println("visitCompoundExpression=>" + expressionBuilder);
        return expressionBuilder.toString();
    }

    @Override
    public String visitUnaryExpression(UnaryExpression unaryExpression, String data) {
        //System.out.println("visitUnaryExpression=>" + expressionBuilder);
        return data + "(" +
                " " + resolveOperator(unaryExpression.getOperator()) + " " +
                unaryExpression.getLeftOperand().accept(this, "") +
                ")";
    }

    @Override
    public String visitExpressionField(ExpressionField field, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        if (fieldMap != null && fieldMap.get(field.infix()) != null) {
            expressionBuilder.append("@.").append(fieldMap.get(field.infix()));
        } else if (fieldValueTransformer != null && fieldValueTransformer.transformField(field.infix()) != null) {
            expressionBuilder.append("@.").append(fieldValueTransformer.transformField(field.infix()));
            fieldStack.push(field); //pushing the field for lookup while visiting value.
        } else {
            expressionBuilder.append("@.").append(field.infix());
        }
        // System.out.println("visitExpressionField=>" + expressionBuilder.toString());

        return expressionBuilder.toString();
    }

    @Override
    public String visitExpressionValue(ExpressionValue<? extends Comparable> value, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        Operator operator = operatorStack.pop();

        if (!fieldStack.isEmpty() && fieldValueTransformer != null) {
            ExpressionField field = fieldStack.pop(); // pop the field associated with this value.
            FieldValuePair fieldValuePair = fieldValueTransformer.transformValue(field.infix(), value.getValue());
            if (fieldValuePair != null && fieldValuePair.getValue() != null) {
                value = new ExpressionValue(fieldValuePair.getValue());
            }
        }
        if (operator == Operator.BETWEEN) {
            List<Comparable> expressionValues = (List<Comparable>) value.getValue();
            expressionBuilder.append("\"").append(expressionValues.get(0)).append("\"")
                    .append(" AND ")
                    .append("\"").append(expressionValues.get(1)).append("\"");
        } else if (operator == Operator.IN) {
            List<Comparable> expressionValues = (List<Comparable>) value.getValue();
            expressionBuilder.append("(");
            for (int i = 0; i < expressionValues.size(); i++) {
                expressionBuilder.append("'").append(expressionValues.get(i)).append("'");
                if (i < expressionValues.size() - 1) {
                    expressionBuilder.append(", ");
                }
            }
            expressionBuilder.append(")");
        } else {
            expressionBuilder.append(resolveValue(operator, value.infix()));
        }
        System.out.println(expressionBuilder);
        return expressionBuilder.toString();
    }

    private String resolveValue(Operator operator, String value) {
        StringBuilder expressionValue = new StringBuilder();
        switch (operator) {

            /* Relational string operators*/
            case EQUALS, CONTAINS -> expressionValue.append("\"").append(value).append("\"");
            case STARTS -> expressionValue.append("\"/").append(value).append(".*/i").append("\"");
            case ENDS -> expressionValue.append("\"/.*").append(value).append("/i\"");


            /* Relational numeric operators*/
            case LT, GT, EQ, GTE, LTE -> expressionValue.append(value);
        }
        return expressionValue.toString();
    }

    private String resolveOperator(Operator operator) {
        return switch (operator) {
            /* Logical operators */
            case AND -> "&&";
            case OR -> "||";
            case NOT -> "!";

            /* Relational string operators*/
            case EQUALS -> "==";
            case CONTAINS, STARTS, ENDS -> "=~";

            /* Relational numeric operators*/
            case LT -> "<";
            case GT -> ">";
            case EQ -> "==";
            case GTE -> ">=";
            case LTE -> "<=";

            /* Common operators */
            case IN -> "IN";
            case BETWEEN -> "BETWEEN";
        };
    }
}
