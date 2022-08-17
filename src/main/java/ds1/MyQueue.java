package ds1;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Possible with 3 solutions : using array, linkedList or a stack (two stacks, one for add, the other for remove)
 */
public class MyQueue {

    private String[] values;
    private int rear = 0;
    private int front = 0;
    private int count = 0;

    public MyQueue(int capacity) {
        this.values = new String[capacity];
    }

    public void add(String value) {
        if( count == values.length)
            throw new IllegalStateException();

        values[rear] = value;
        rear = (rear + 1) % values.length;
        count++;
    }

    public String remove() {
        if (isEmpty())
            throw new IllegalStateException();

        final String head = values[front];
        values[front] = null;
        front = (front + 1) % values.length;
        count--;
        return head;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public boolean isFull() {
        return count == values.length;
    }

    public String toString() {
        return Stream.of(values).filter(Objects::nonNull).collect(Collectors.joining(", ", "[", "]"));
    }

    public String element() {
        if (isEmpty())
            return null;
        return values[front];
    }
}
