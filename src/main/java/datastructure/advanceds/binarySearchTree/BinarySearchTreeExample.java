package datastructure.advanceds.binarySearchTree;

import datastructure.advanceds.NamesUtils;

import java.util.*;
import java.util.function.Consumer;

public class BinarySearchTreeExample {

    private final BinaryTree<String> binaryTree = new BinaryTree<>();

    public BinarySearchTreeExample() {
        addNodes();
        searchNodeWithValue(NamesUtils.AHMED);
        searchNodeWithValue("Nassim");
        findSmallestNodeValue();
        dfsTraversal();
        bfsTraversal();
        System.out.println("=== FIN ===");
    }

    private void bfsTraversal() {
        binaryTree.bfsTraversal();
    }

    private void dfsTraversal() {
        System.out.println(" === pre-order traversal ===");
        binaryTree.dfsTraversal(BinaryTree.DfsTraversalStrategy.PRE_ORDER);
        System.out.println("\n === in-order traversal ===");
        binaryTree.dfsTraversal(BinaryTree.DfsTraversalStrategy.IN_ORDER);
        System.out.println("\n === post-order traversal ===");
        binaryTree.dfsTraversal(BinaryTree.DfsTraversalStrategy.POST_ORDER);
        System.out.println("\n");
    }

    private void findSmallestNodeValue() {
        System.out.println("Smallest node value is : " + binaryTree.findSmallestOrGreatest(BinaryTree.Operator.SMALLEST));
        System.out.println("Greatest node value is : " + binaryTree.findSmallestOrGreatest(BinaryTree.Operator.GREATEST));
    }

    private void searchNodeWithValue(String value) {
        System.out.println("Search : " + value + " : " + binaryTree.findNode(value));
    }

    private void addNodes() {
        NamesUtils.NAMES.forEach(binaryTree::insert);
    }
}

class BinaryTree<T extends Comparable<T>> {

    Node<T> root;

    private final Map<DfsTraversalStrategy, Consumer<Node<T>>> TRAVERSAL_CONSUMERS;

    {
        TRAVERSAL_CONSUMERS = new HashMap<>();
        TRAVERSAL_CONSUMERS.put(DfsTraversalStrategy.PRE_ORDER, this::preOrderTraversal);
        TRAVERSAL_CONSUMERS.put(DfsTraversalStrategy.IN_ORDER, this::inOrderTraversal);
        TRAVERSAL_CONSUMERS.put(DfsTraversalStrategy.POST_ORDER, this::postOrderTraversal);
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
        return node;
    }

    public boolean findNode(T value) {
        return findNode(root, value);
    }

    private boolean findNode(Node<T> node, T value) {
        if (Objects.isNull(node))
            return false;

        if (node.value.compareTo(value) == 0)
            return true;

        return node.value.compareTo(value) > 0
                ? findNode(node.left, value)
                : findNode(node.right, value);
    }

    public T findSmallestOrGreatest(Operator operator) {
        return operator.isSmallest() ? findSmallest(root) : findGreatest(root);
    }

    private T findGreatest(Node<T> node) {
        return Objects.isNull(node.right)
                ? node.value
                : findGreatest(node.right);
    }

    private T findSmallest(Node<T> node) {
        return Objects.isNull(node.left)
                ? node.value
                : findSmallest(node.left);
    }

    public void dfsTraversal(DfsTraversalStrategy dfsStrategy) {
        TRAVERSAL_CONSUMERS.get(dfsStrategy).accept(root);
    }

    private void preOrderTraversal(Node<T> node) {
        if (Objects.nonNull(node)) {
            System.out.print(node.value + ", ");
            preOrderTraversal(node.left);
            preOrderTraversal(node.right);
        }
    }

    private void inOrderTraversal(Node<T> node) {
        if (Objects.nonNull(node)) {
            inOrderTraversal(node.left);
            System.out.print(node.value + ", ");
            inOrderTraversal(node.right);
        }
    }

    private void postOrderTraversal(Node<T> node) {
        if (Objects.nonNull(node)) {
            postOrderTraversal(node.left);
            postOrderTraversal(node.right);
            System.out.print(node.value + ", ");
        }
    }

    public void bfsTraversal() {
        System.out.println(" === Breadth first traversal ===");
        if (Objects.isNull(root))
            return;

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> removed = queue.remove();
            System.out.print(" " + removed.value);

            if (Objects.nonNull(removed.left))
                queue.add(removed.left);

            if (Objects.nonNull(removed.right))
                queue.add(removed.right);
        }
        System.out.println("\n");
    }

    static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        public Node(T value) {
            this.value = value;
        }
    }

    enum Operator {
        SMALLEST, GREATEST;

        public boolean isSmallest() {
            return this == SMALLEST;
        }
    }

    enum DfsTraversalStrategy {
        PRE_ORDER, IN_ORDER, POST_ORDER;
    }
}