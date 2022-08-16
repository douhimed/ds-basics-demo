package ds1;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Function;

public class MyStack {

    private static final List<Character> OPEN_BRACKETS = Arrays.asList('(', '{', '[', '<');
    private static final List<Character> CLOSING_BRACKETS = Arrays.asList(')', '}', ']', '>');

    public static boolean isBalanced(String expression) {
        if (Objects.isNull(expression))
            throw new IllegalArgumentException();

        final char[] chars = expression.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (Character ch : chars) {
            if (isBracketInGoodPosition(OPEN_BRACKETS::contains, ch))
                stack.push(ch);
            else if (isBracketInGoodPosition(CLOSING_BRACKETS::contains, ch)) {
                if (stack.isEmpty()) return false;

                final Character pop = stack.pop();
                if (!isBracketsMatchesPosition(pop, ch)) return false;
            }
        }
        return true;
    }

    private static boolean isBracketsMatchesPosition(Character left, Character right) {
        return OPEN_BRACKETS.indexOf(left) == CLOSING_BRACKETS.indexOf(right);
    }

    private static boolean isBracketInGoodPosition(Function<Character, Boolean> function, Character ch) {
        return function.apply(ch);
    }

}
