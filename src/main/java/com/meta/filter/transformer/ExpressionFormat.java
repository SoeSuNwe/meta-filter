package com.meta.filter.transformer;

public enum ExpressionFormat {
    SQL("SQL"),

    INFIX("INFIX"),
    INFIX_CUSTOM_OPERATOR("INFIX_CUSTOM_OPERATOR");
    private final String type;

    ExpressionFormat(String type) {
        this.type = type;
    }
    public  static ExpressionFormat getFormat(String type){
        for (ExpressionFormat format : ExpressionFormat.values()) {
            if (format.type.equals(type)) {
                return format;
            }
        }
        return SQL;
    }


}
