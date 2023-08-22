package com.meta.filter.transformer;


import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseFilterExpression {
    private  String expression;
    public String getExpression() {
        return expression;
    }
    public void setExpression(String jsonString,ExpressionFormat format) throws IOException {
        Map mapper = new ObjectMapper().readValue(jsonString, Map.class);
        FilterExpression.FilterExpressionBuilder builder = FilterExpression.newFilterExpressionBuilder();
        FilterExpression filterExpression = builder.args(mapper)
                .build();
        this.expression = filterExpression.getExpression(format);
    }
}
