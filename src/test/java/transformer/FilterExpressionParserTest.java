package transformer;

import com.meta.filter.assignment.Expression;
import com.meta.filter.transformer.FilterExpressionParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class FilterExpressionParserTest {
    @Test
    public void binaryExpressionTest() {
        // Create the mock data.
        Map<String, Object> argMaps = createBinaryFilterArgMap("firstName", "contains", "ABC");

        // Invoke method under test.
        FilterExpressionParser expressionParser = new FilterExpressionParser();
        Expression expression = expressionParser.parseFilterExpression(argMaps);

        // Verify assertions.
        Assert.assertNotNull(expression);
        Assert.assertEquals("(firstName contains ABC)", expression.infix());
    }

    @Test
    public void compoundExpressionTest() {
        // Create the mock data.
        Map<String, Object> argMaps = createCompoundFilterArgMap();

        // Invoke method under test.
        FilterExpressionParser expressionParser = new FilterExpressionParser();
        Expression expression = expressionParser.parseFilterExpression(argMaps);

        // Verify assertions.
        Assert.assertNotNull(expression);
        Assert.assertEquals("((firstName contains ABC) or (lastName equals XYZ))", expression.infix());
    }


    private Map<String, Object> createBinaryFilterArgMap(String left, String op, String right) {
        Map<String, Object> argsMap = new LinkedHashMap<>();
        Map<String, String> nestedMap = new LinkedHashMap<>();
        nestedMap.put(op,right);
        argsMap.put(left,nestedMap);
        return argsMap;
    }

    private Map<String, Object> createCompoundFilterArgMap() {
        Map<String, Object> argsMap = new HashMap<>();
        List<Map> mapList = new ArrayList<>();
        mapList.add(createBinaryFilterArgMap("firstName", "contains", "ABC"));
        mapList.add(createBinaryFilterArgMap("lastName", "equals", "XYZ"));
        argsMap.put("or",mapList);
        return argsMap;
    }
}
