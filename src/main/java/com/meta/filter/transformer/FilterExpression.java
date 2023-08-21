package com.meta.filter.transformer;

import com.meta.filter.assignment.Expression;
import com.meta.filter.visitor.ExpressionVisitor;

import java.util.HashMap;
import java.util.Map;

public class FilterExpression {
    private final Map<String,String> fieldMap;
    private final Expression expressionAst;
    private final FieldValueTransformer fieldValueTransformer;

    private FilterExpression(FilterExpressionBuilder expressionBuilder) {
        this.fieldMap = expressionBuilder.fieldMap;
        this.expressionAst = expressionBuilder.expressionAst;
        this.fieldValueTransformer = expressionBuilder.fieldValueTransformer;
    }

    public static class FilterExpressionBuilder {

        private final Map<String,String> fieldMap;
        private Expression expressionAst;
        private Map args;
        private FieldValueTransformer fieldValueTransformer;

        private FilterExpressionBuilder () {
            fieldMap = new HashMap<>();
        }

        public FilterExpressionBuilder args(Map filterArgs) {
            this.args = filterArgs;
            return this;
        }

        public FilterExpression build() {
            FilterExpressionParser expressionParser = new FilterExpressionParser();
            if (args != null) {
                String FILTER_ARG = "filter";
                Object filter = args.get(FILTER_ARG);
                if (filter != null) {
                    expressionAst = expressionParser.parseFilterExpression((Map) filter);
                }
            }
            return new FilterExpression(this);
        }
    }

    public static FilterExpressionBuilder newFilterExpressionBuilder() {
        return new FilterExpressionBuilder();
    }

    public <T> T getExpression(ExpressionFormat format) {
        if (expressionAst == null) {
            throw new InvalidFilterException("Missing or invalid filter arguments");
        }
        ExpressionVisitor<T> expressionVisitor = ExpressionVisitorFactory.getExpressionVisitor(format, fieldMap, fieldValueTransformer);
        return expressionVisitor.expression(expressionAst);
    }
}
