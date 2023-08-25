package visitor;

import com.meta.filter.transformer.BaseFilterExpression;
import constant.SampleQueryConstant;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class InfixExpressionTest extends BaseFilterExpression {
    @Test
    public void filterExpressionSimple() throws IOException {
        String exp= SampleQueryConstant.BINARY_FILER;
        System.out.println(exp);
        setExpression(exp);
        String expression = getExpression();
        String expectedExpression = "(@.firstName =~ \"Saurabh\")";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionSimpleInt() throws IOException {
        String exp= SampleQueryConstant.BINARY_FILER_INT;
        System.out.println(exp);
        setExpression(exp);
        String expression = getExpression();
        String expectedExpression = "(@.age >= 25)";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionWithOR() throws IOException {
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_OR;
        System.out.println(exp);
        setExpression(exp);
        String expression = getExpression();
        String expectedExpression = "((@.firstName =~ \"Saurabh\") || (@.lastName == \"Jaiswal\"))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionWithAND() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_AND;
        System.out.println(exp);
        setExpression(exp);
        String expression = getExpression();
        String expectedExpression = "((@.firstName =~ \"Saurabh\") && (@.lastName == \"Jaiswal\"))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionANDWithOR() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_AND_OR;
        System.out.println(exp);
        setExpression(exp);
        String expression = getExpression();
        String expectedExpression = "((@.firstName =~ \"Saurabh\") && ((@.lastName == \"Jaiswal\") || (@.age >= 25)))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionORWithAND() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_OR_AND;
        System.out.println(exp);
        setExpression(exp);
        String expression = getExpression();
        String expectedExpression = "((@.firstName =~ \"Saurabh\") || ((@.lastName == \"Jaiswal\") && (@.age >= 25)))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionANDWithMultipleOR() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_OR_OR_AND;
        System.out.println(exp);
        setExpression(exp);
        String expression = getExpression();
        String expectedExpression = "(((@.firstName =~ \"Saurabh\") || (@.lastName == \"Jaiswal\")) || ((@.firstName == \"Vinod\") && (@.age >= 30)))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionANDWithMultipleAND() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_AND_AND_OR;
        System.out.println(exp);
        setExpression(exp);
        String expression = getExpression();
        String expectedExpression = "(((@.firstName =~ \"/Saurabh.*/i\") && (@.lastName == \"Jaiswal\")) && ((@.firstName =~ \"/.*Vinod/i\") || (@.age <= 30)))";
        Assert.assertEquals(expectedExpression, expression);
    }

    @Test
    public void filterExpressionNOTFilter() throws IOException{
        String exp= SampleQueryConstant.NOT_FILTER;
        System.out.println(exp);
        setExpression(exp);
        String expression = getExpression();
        String expectedExpression = "( ! (@.firstName == \"Saurabh\"))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionCompoundDateFilter() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_DATE_FILTER;
        System.out.println(exp);
        setExpression(exp);
        String expression = getExpression();
        String expectedExpression = "((@.lastName == \"Jaiswal\") || (@.birthDate > 1996-12-19T16:39:57-08:00))";
        Assert.assertEquals(expectedExpression, expression);
    }
}
