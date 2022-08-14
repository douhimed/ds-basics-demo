package ds1;

import java.util.NoSuchElementException;
import java.util.Objects;

public class Linked<T> {

    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        if (isEmpty())
            first = last = node;
        else {
            node.next = first;
            first = node;
        }
        size++;
    }

    private boolean isEmpty() {
        return Objects.isNull(first);
    }

    public void addLast(T value) {
        Node<T> node = new Node<>(value);
        if (isEmpty())
            first = last = node;
        else {
            last.next = node;
            last = node;
        }
        size++;
    }

    public void deleteFirst() {
        Node<T> next = first.next;
        first.next = null;
        first = next;
        if (size > 0) size--;
    }

    public void deleteLast() {
        checkIfEmpty();

        if (first == last) {
            first = last = null;
        } else {
            Node<T> previous = getPreviousTo(last);
            if (Objects.nonNull(previous)) {
                last = previous;
                last.next = null;
            }
        }
        if (size > 0) size--;
    }

    private void checkIfEmpty() {
        if (isEmpty())
            throw new NoSuchElementException("Already empty");
    }

    private Node<T> getPreviousTo(Node<T> node) {
        Node<T> current = first;
        while (Objects.nonNull(current)) {
            if (current.next == node)
                return current;
            current = current.next;
        }
        return null;
    }

    public boolean contains(T value) {
        return indexOf(value) >= 0;
    }

    public int size() {
        return size;
    }

    public String print() {
        if (isEmpty())
            return "";

        StringBuilder s = new StringBuilder(first.value.toString());
        Node<T> next = first.next;
        while (Objects.nonNull(next)) {
            s.append(", ").append(next.value);
            next = next.next;
        }
        return s.toString();
    }

    public int indexOf(T value) {
        int index = 0;
        Node<T> current = first;
        while (Objects.nonNull(current)) {
            if (current.value.equals(value))
                return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    public void reverse() {
        try {
            checkIfEmpty();

            Node<T> previous = first;
            Node<T> current = first.next;
            while (Objects.nonNull(current)) {
                Node<T> next = current.next;
                current.next = previous;
                previous = current;
                current = next;
            }

            last = first;
            last.next = null;
            first = previous;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public T getKthFromTheEnd(int index) {
        if (isEmpty() || isIndexValid(index))
            return null;

        Node<T> secondNode = first;
        for (int i = 0; i < index - 1; i++)
            secondNode = secondNode.next;

        Node<T> firstNode = first;
        while (secondNode != last) {
            secondNode = secondNode.next;
            firstNode = firstNode.next;
        }

        return firstNode.value;
    }

    private boolean isIndexValid(int index) {
        return index < 0 || index > size - 1;
    }

    private static class Node<T> {
        private final T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
