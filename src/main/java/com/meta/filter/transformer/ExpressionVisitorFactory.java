package com.meta.filter.transformer;

import com.meta.filter.visitor.ExpressionVisitor;
import com.meta.filter.visitor.InfixExpressionVisit;

import java.util.Map;

public class ExpressionVisitorFactory {
    static ExpressionVisitor getExpressionVisitor(Map<String, String> fieldMap,
                                                  FieldValueTransformer fieldValueTransformer) {
        return new InfixExpressionVisit(fieldMap, fieldValueTransformer);
    }
}
