package com.meta.filter;

import com.meta.filter.transformer.BaseFilterExpression;
import com.meta.filter.transformer.ExpressionFormat;

import java.io.IOException;

public class MetaFilterExpression extends BaseFilterExpression {
    public static void main(String[] args) throws IOException {
        String exp = "{" +
                "\"filter\":" +
                "{\"firstName\":{\"contains\":\"Saurabh\"}}" +
                "}";
        String format = "SQL";
        String filterExp = transForm(exp, format);
        System.out.println("output filterExp => " + filterExp);
    }

    public static String transForm(String jsonString, String format) throws IOException {

        BaseFilterExpression baseFilterExpression = new BaseFilterExpression() {
            @Override
            public String getExpression() {
                return super.getExpression();
            }

            @Override
            public void setExpression(String jsonString, ExpressionFormat format) throws IOException {
                super.setExpression(jsonString, format);
            }
        };
        baseFilterExpression.setExpression(jsonString, ExpressionFormat.getFormat(format));
        return baseFilterExpression.getExpression();
    }
}

