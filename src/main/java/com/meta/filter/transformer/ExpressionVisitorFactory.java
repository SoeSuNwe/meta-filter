package com.meta.filter.transformer;

import com.meta.filter.visitor.ExpressionVisitor;
import com.meta.filter.visitor.InfixExpressionVisitor;
import com.meta.filter.visitor.SQLExpressionVisitor;

import java.util.Map;

public class ExpressionVisitorFactory {
    static ExpressionVisitor getExpressionVisitor(ExpressionFormat format,
                                                  Map<String, String> fieldMap,
                                                  FieldValueTransformer fieldValueTransformer) {
        ExpressionVisitor expressionVisitor = new InfixExpressionVisitor(fieldMap, fieldValueTransformer);
        if (format != null) {
            switch (format) {
                case INFIX:
                    expressionVisitor = new InfixExpressionVisitor(fieldMap, fieldValueTransformer);
                    break;
                case SQL:
                    expressionVisitor = new SQLExpressionVisitor(fieldMap, fieldValueTransformer);
                    break;
            }
        }
        return expressionVisitor;
    }
}
