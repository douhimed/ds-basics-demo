import ds1.MyStack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyStackTests {

    private List<String> balancedExpressions;
    private List<String> unbalancedExpressions;
    private MyStack<String> emptyStack;
    private MyStack<Integer> stackValues;

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

        emptyStack = new MyStack<>();

        stackValues = new MyStack<>();
        for (int i = 1; i <= 3; i++) {
            stackValues.push(i * 10);
        }
    }

    @Test
    public void isBalanced_ShouldReturnTrueIfBracketsAreBalancedOtherwiseFalse() {
        balancedExpressions.forEach(expression -> assertTrue(MyStack.isBalanced(expression)));
        unbalancedExpressions.forEach(expression -> Assert.assertFalse(MyStack.isBalanced(expression)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isBalanced_InCaseOfNullShouldThrowIllegalArgumentException() {
        MyStack.isBalanced(null);
    }

    @Test
    public void push_ShouldAddTheElementl() {
        emptyStack = new MyStack<>();
        assertTrue(emptyStack.isEmpty());
        emptyStack.push("A");
        emptyStack.push("B");
        emptyStack.push("C");
        assertFalse(emptyStack.isEmpty());
        assertEquals("[A, B, C]", emptyStack.print());
    }

    @Test
    public void peek_SHouldReturnTHeLastElementAdddedWithoutDeletedIt() {
        assertEquals(Integer.valueOf(30), stackValues.peek());
        assertFalse(stackValues.isEmpty());
    }

    @Test
    public void pop_ShouldReturnTheLastElementAddedAndDeletedItFromStack() {
        for (int i = 3; i > 0; i--) {
            assertEquals(Integer.valueOf(i * 10), stackValues.pop());
            assertEquals( i == 1, stackValues.isEmpty());
        }
    }
}
