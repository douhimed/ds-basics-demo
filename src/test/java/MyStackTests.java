import ds1.MyStack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MyStackTests {

    private List<String> balancedExpressions;
    private List<String> unbalancedExpressions;

    @Before
    public void setUp() throws Exception {
        balancedExpressions = new ArrayList<>();
        balancedExpressions.add("");
        balancedExpressions.add("()");
        balancedExpressions.add("((<>))");
        balancedExpressions.add("(())[]");
        balancedExpressions.add("({}<>)[]");

        unbalancedExpressions = new ArrayList<>();
        unbalancedExpressions.add(")(");
        unbalancedExpressions.add("())");
        unbalancedExpressions.add(")(())");
        unbalancedExpressions.add("}(){");
        unbalancedExpressions.add("{})<[]");
    }

    @Test
    public void isBalanced_ShouldReturnTrueIfBracketsAreBalancedOtherwiseFalse() {
        balancedExpressions.forEach(expression -> Assert.assertTrue(MyStack.isBalanced(expression)));
        unbalancedExpressions.forEach(expression -> Assert.assertFalse(MyStack.isBalanced(expression)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isBalanced_InCaseOfNullShouldThrowIllegalArgumentException() {
        MyStack.isBalanced(null);
    }
}
