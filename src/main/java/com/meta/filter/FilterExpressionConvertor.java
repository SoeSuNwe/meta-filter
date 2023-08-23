package com.meta.filter;

import com.meta.filter.transformer.BaseFilterExpression;

import java.io.IOException;

public class FilterExpressionConvertor extends BaseFilterExpression {
    public static void main(String[] args) throws IOException {
        String exp = "{" +
                "\"filter\":" +
                "{\"firstName\":{\"contains\":\"Saurabh\"}}" +
                "}";
        String filterExp = convert(exp);
        System.out.println("output filterExp => " + filterExp);
    }

    public static String convert(String jsonString) throws IOException {

        BaseFilterExpression baseFilterExpression = new BaseFilterExpression() {
            @Override
            public String getExpression() {
                return super.getExpression();
            }

            @Override
            public void setExpression(String jsonString) throws IOException {
                super.setExpression(jsonString);
            }
        };
        baseFilterExpression.setExpression(jsonString);
        return baseFilterExpression.getExpression();
    }
}

