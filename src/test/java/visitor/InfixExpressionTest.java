package visitor;

import com.meta.filter.transformer.BaseFilterExpression;
import com.meta.filter.transformer.ExpressionFormat;
import constant.SampleQueryConstant;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class InfixExpressionTest extends BaseFilterExpression {
    @Test
    public void filterExpressionSimple() throws IOException {
        String exp= SampleQueryConstant.BINARY_FILER;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.INFIX);
        String expression = getExpression();
        String expectedExpression = "(firstName contains Saurabh)";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionSimpleInt() throws IOException {
        String exp= SampleQueryConstant.BINARY_FILER_INT;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.INFIX);
        String expression = getExpression();
        String expectedExpression = "(age gte 25)";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionWithOR() throws IOException {
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_OR;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.INFIX);
        String expression = getExpression();
        String expectedExpression = "((firstName contains Saurabh) or (lastName equals Jaiswal))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionWithAND() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_AND;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.INFIX);
        String expression = getExpression();
        String expectedExpression = "((firstName contains Saurabh) and (lastName equals Jaiswal))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionANDWithOR() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_AND_OR;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.INFIX);
        String expression = getExpression();
        String expectedExpression = "((firstName contains Saurabh) and ((lastName equals Jaiswal) or (age gte 25)))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionORWithAND() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_OR_AND;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.INFIX);
        String expression = getExpression();
        String expectedExpression = "((firstName contains Saurabh) or ((lastName equals Jaiswal) and (age gte 25)))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionANDWithMultipleOR() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_OR_OR_AND;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.INFIX);
        String expression = getExpression();
        String expectedExpression = "(((firstName contains Saurabh) or (lastName equals Jaiswal)) or ((firstName equals Vinod) and (age gte 30)))";
        Assert.assertEquals(expectedExpression, expression);
    }
}
