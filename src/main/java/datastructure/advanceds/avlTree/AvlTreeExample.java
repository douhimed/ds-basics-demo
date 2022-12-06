package datastructure.advanceds.avlTree;

import datastructure.advanceds.NamesUtils;

import java.util.Objects;

public class AvlTreeExample {

    public AvlTreeExample() {
        AvlTree<String> avlTree = new AvlTree<>();
        NamesUtils.NAMES.forEach(avlTree::insert);
        System.out.println("=== FIN ===");
    }
}

class AvlTree<T extends Comparable<T>> {

    private Node<T> root;

    Node<T> rotateRight(Node<T> y) {
        Node<T> x = y.left;
        Node<T> z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    Node<T> rotateLeft(Node<T> y) {
        Node<T> x = y.right;
        Node<T> z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    Node<T> rebalance(Node<T> node) {
        updateHeight(node);
        int balance = getBalance(node);
        if (balance > 1) {
            if (getHeight(node.right.right) > getHeight(node.right.left)) {
                node = rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        } else if (balance < -1) {
            if (getHeight(node.left.left) > getHeight(node.left.right)) {
                node = rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        }
        return node;
    }

    void insert(T value) {
        root = insert(root, value);
    }

    private Node<T> insert(Node<T> node, T value) {
        if (Objects.isNull(node))
            return new Node<>(value);
        else if (node.value.compareTo(value) > 0)
            node.left = insert(node.left, value);
        else if (node.value.compareTo(value) < 0)
            node.right = insert(node.right, value);
        else
            throw new RuntimeException("Duplicate Key/value !!!");
        return rebalance(node);
    }

    void updateHeight(Node<T> node) {
        node.height = 1 + getMaxheightOfNode(node);
    }

    private int getMaxheightOfNode(Node<T> node) {
        return Math.max(getHeight(node.left), getHeight(node.right));
    }

    public int getHeight(Node<T> node) {
        return Objects.isNull(node) ? -1 : node.height;
    }

    public int getBalance(Node<T> node) {
        return Objects.isNull(node) ? 0 : getHeight(node.right) - getHeight(node.left);
    }

    private static class Node<T> {
        private T value;
        private int height;
        private Node<T> left;
        private Node<T> right;

        public Node(T value) {
            this.value = value;
        }
    }
}
