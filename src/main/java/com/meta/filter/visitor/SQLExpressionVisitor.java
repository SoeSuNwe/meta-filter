package com.meta.filter.visitor;

import com.meta.filter.assignment.*;
import com.meta.filter.transformer.FieldValuePair;
import com.meta.filter.transformer.FieldValueTransformer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class SQLExpressionVisitor implements ExpressionVisitor<String>{
    private Deque<Operator> operatorStack;
    private Map<String, String> fieldMap;
    private Deque<ExpressionField> fieldStack;
    private FieldValueTransformer fieldValueTransformer;

    public SQLExpressionVisitor(Map<String,String> fieldMap, FieldValueTransformer fieldValueTransformer) {
        this.operatorStack = new ArrayDeque<>();
        this.fieldMap = fieldMap;
        this.fieldStack = new ArrayDeque<>();
        this.fieldValueTransformer = fieldValueTransformer;
    }

    /**
     * Returns the SQL WHERE clause string
     * from the expression tree.
     * @return
     * @param expression
     */
    @Override
    public String expression(Expression expression) {
        String expressionString = "WHERE ";
        if (expression != null) {
            expressionString = expression.accept(this, expressionString);
        }
        System.out.println("\n SQL expression => " + expressionString + "\n\n ---------------------------------------------------------------");
        return expressionString;
    }

    /**
     * Handles the processing of compound
     * expression node.
     * @param compoundExpression
     *          Contains compound expression.
     * @param data
     *          Buffer for storing processed data.
     * @return
     *          Data of processed node.
     */
    @Override
    public String visitCompoundExpression(CompoundExpression compoundExpression, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        expressionBuilder.append("(")
                .append(compoundExpression.getLeftOperand().accept(this, ""))
                .append(" ").append(compoundExpression.getOperator().getName().toUpperCase()).append(" ")
                .append(compoundExpression.getRightOperand().accept(this, ""))
                .append(")");
        return expressionBuilder.toString();

    }

    /**
     * Handles the processing of binary
     * expression node.
     * @param binaryExpression
     *          Contains binary expression.
     * @param data
     *          Buffer for storing processed data.
     * @return
     *          Data of processed node.
     */
    @Override
    public String visitBinaryExpression(BinaryExpression binaryExpression, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        expressionBuilder.append("(")
                .append(binaryExpression.getLeftOperand().accept(this, ""))
                .append(" ").append(resolveOperator(binaryExpression.getOperator())).append(" ");
        operatorStack.push(binaryExpression.getOperator());
        expressionBuilder.append(binaryExpression.getRightOperand().accept(this, ""))
                .append(")");
        return expressionBuilder.toString();
    }

    /**
     * Handles the processing of unary
     * expression node.
     * @param unaryExpression
     *          Contains unary expression.
     * @param data
     *          Buffer for storing processed data.
     * @return
     *          Data of processed node.
     */
    @Override
    public String visitUnaryExpression(UnaryExpression unaryExpression, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        expressionBuilder.append("(")
                .append(" ").append(resolveOperator(unaryExpression.getOperator())).append(" ")
                .append(unaryExpression.getLeftOperand().accept(this, ""))
                .append(")");
        return expressionBuilder.toString();
    }

    /**
     * Handles the processing of expression
     * field node.
     * @param field
     *          Contains expression field.
     * @param data
     *          Buffer for storing processed data.
     * @return
     *          Data of processed node.
     */
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
        return expressionBuilder.toString();
    }

    /**
     * Handles the processing of expression
     * value node.
     * @param value
     *          Contains expression value.
     * @param data
     *          Buffer for storing processed data.
     * @return
     *          Data of processed node.
     */
    @Override
    public String visitExpressionValue(ExpressionValue<? extends Comparable> value, String data) {
        StringBuilder expressionBuilder = new StringBuilder(data);
        Operator operator = operatorStack.pop();

        if (!fieldStack.isEmpty() && fieldValueTransformer != null) {
            ExpressionField field  = fieldStack.pop(); // pop the field associated with this value.
            FieldValuePair fieldValuePair = fieldValueTransformer.transformValue(field.infix(),value.getValue());
            if (fieldValuePair != null && fieldValuePair.getValue() != null) {
                value = new ExpressionValue(fieldValuePair.getValue());
            }
        }

        if (operator == Operator.STARTS) {
            expressionBuilder.append("'").append(value.infix()).append("%").append("'");
        } else if (operator == Operator.ENDS) {
            expressionBuilder.append("'").append("%").append(value.infix()).append("'");
        } else if (operator == Operator.CONTAINS) {
            expressionBuilder.append("'").append("%").append(value.infix()).append("%").append("'");
        } else if(operator == Operator.BETWEEN)  {
            List<Comparable> expressionValues = (List<Comparable>)value.getValue();
            expressionBuilder.append("'").append(expressionValues.get(0)).append("'")
                    .append(" AND ")
                    .append("'").append(expressionValues.get(1)).append("'");
        } else if (operator == Operator.IN) {
            List<Comparable> expressionValues = (List<Comparable>)value.getValue();
            expressionBuilder.append("(");
            for (int i = 0; i < expressionValues.size(); i++) {
                expressionBuilder.append("'").append(expressionValues.get(i)).append("'");
                if (i < expressionValues.size() - 1) {
                    expressionBuilder.append(", ");
                }
            }
            expressionBuilder.append(")");
        } else {
            expressionBuilder.append("'").append(value.infix()).append("'");
        }
        return expressionBuilder.toString();
    }

    private String resolveOperator(Operator operator) {
        String op = "";
        switch (operator) {
            /* Logical operators */
            case AND:
            case OR:
            case NOT:
                op = operator.getName().toUpperCase();
                break;

            /* Relational string operators*/
            case EQUALS:
                op = "=";
                break;
            case CONTAINS:
            case STARTS:
            case ENDS:
                op = "LIKE";
                break;

            /* Relational numeric operators*/
            case LT:
                op = "<";
                break;
            case GT:
                op = ">";
                break;
            case EQ:
                op = "=";
                break;
            case GTE:
                op = ">=";
                break;
            case LTE:
                op = "<=";
                break;

            /* Common operators */
            case IN:
                op = "IN";
                break;
            case BETWEEN:
                op = "BETWEEN";
                break;
        }
        return op;
    }
}
