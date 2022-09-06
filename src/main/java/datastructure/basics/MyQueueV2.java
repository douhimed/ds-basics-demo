package datastructure.basics;

import java.util.Stack;

public class MyQueueV2<T> {

    private final Stack<T> addingStack = new Stack<>();
    private final Stack<T> removeStack = new Stack<>();

    public void add(T value) {
        addingStack.add(value);
    }

    public T remove() {
        if(isEmpty())
            throw new IllegalStateException();

        refillDeletingStack();

        return removeStack.pop();
    }

    public T peek() {
        if(isEmpty())
            throw new IllegalStateException();

        refillDeletingStack();

        return removeStack.peek();
    }

    public boolean isEmpty() {
        return removeStack.isEmpty() && addingStack.isEmpty();
    }

    private void refillDeletingStack() {
        if(removeStack.isEmpty())
            while (!addingStack.isEmpty())
                removeStack.add(addingStack.pop());
    }

}
