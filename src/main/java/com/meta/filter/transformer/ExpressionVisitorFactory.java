package com.meta.filter.transformer;

import com.meta.filter.visitor.ExpressionVisitor;
import com.meta.filter.visitor.InfixExpressionCustomOperatorVisit;
import com.meta.filter.visitor.InfixExpressionVisitor;
import com.meta.filter.visitor.SQLExpressionVisitor;

import java.util.Map;

public class ExpressionVisitorFactory {
    static ExpressionVisitor getExpressionVisitor(ExpressionFormat format,
                                                  Map<String, String> fieldMap,
                                                  FieldValueTransformer fieldValueTransformer) {
        ExpressionVisitor expressionVisitor = new InfixExpressionVisitor(fieldMap, fieldValueTransformer);
        if (format != null) {
            expressionVisitor = switch (format) {
                case INFIX -> new InfixExpressionVisitor(fieldMap, fieldValueTransformer);
                case SQL -> new SQLExpressionVisitor(fieldMap, fieldValueTransformer);
                case INFIX_CUSTOM_OPERATOR -> new InfixExpressionCustomOperatorVisit(fieldMap, fieldValueTransformer);
            };
        }
        return expressionVisitor;
    }
}
