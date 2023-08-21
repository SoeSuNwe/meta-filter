package visitor;

import com.meta.filter.transformer.ExpressionFormat;
import constant.SampleQueryConstant;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SQLExpressionTest extends BaseFilterExpressionTest {
    @Test
    public void filterExpressionSimple() throws IOException {
        String exp= SampleQueryConstant.BINARY_FILER;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.SQL);
        String expression = getExpression();
        String expectedExpression = "WHERE (firstName LIKE '%Saurabh%')";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionSimpleInt() throws IOException {
        String exp= SampleQueryConstant.BINARY_FILER_INT;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.SQL);
        String expression = getExpression();
        String expectedExpression = "WHERE (age >= '25')";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionWithOR() throws IOException {
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_OR;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.SQL);
        String expression = getExpression();
        String expectedExpression = "WHERE ((firstName LIKE '%Saurabh%') OR (lastName = 'Jaiswal'))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionWithAND() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_AND;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.SQL);
        String expression = getExpression();
        String expectedExpression = "WHERE ((firstName LIKE '%Saurabh%') AND (lastName = 'Jaiswal'))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionANDWithOR() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_AND_OR;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.SQL);
        String expression = getExpression();
        String expectedExpression = "WHERE ((firstName LIKE '%Saurabh%') AND ((lastName = 'Jaiswal') OR (age >= '25')))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionORWithAND() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_OR_AND;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.SQL);
        String expression = getExpression();
        String expectedExpression = "WHERE ((firstName LIKE '%Saurabh%') OR ((lastName = 'Jaiswal') AND (age >= '25')))";
        Assert.assertEquals(expectedExpression, expression);
    }
    @Test
    public void filterExpressionANDWithMultipleOR() throws IOException{
        String exp= SampleQueryConstant.COMPOUND_FILER_WITH_OR_OR_AND;
        System.out.println(exp);
        setExpression(exp, ExpressionFormat.SQL);
        String expression = getExpression();
        String expectedExpression = "WHERE (((firstName LIKE '%Saurabh%') OR (lastName = 'Jaiswal')) OR ((firstName = 'Vinod') AND (age >= '30')))";
        Assert.assertEquals(expectedExpression, expression);
    }

}
