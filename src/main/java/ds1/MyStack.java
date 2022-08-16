package ds1;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyStack<T> {

    private static final List<Character> OPEN_BRACKETS = Arrays.asList('(', '{', '[', '<');
    private static final List<Character> CLOSING_BRACKETS = Arrays.asList(')', '}', ']', '>');

    private List<T> values = new ArrayList<>();
    private int count = 0;


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

    public boolean isEmpty() {
        return count == 0;
    }

    public void push(T value) {
        values.add(value);
        count++;
    }


    public String print() {
        return values.stream().filter(Objects::nonNull)
                .map(Object::toString).collect(Collectors.joining(", ", "[", "]"));
    }

    public T peek() {
        return values.get(count - 1);
    }

    public T pop() {
        T value = values.get(count - 1);
        values.set(count - 1, null);
        count--;
        return value;
    }
}
