package datastructure.advanceds.binarytrees;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Tree is a  data Structure with hierarchy storage strategy
 * <p>
 * Node : element of the tree
 * Root : top node
 * Edge : connection between nodes
 * Leaf : a node with no children
 * <p>
 * The binary tree is a specific type of tree where each node has 2 children at max
 * <p>
 * The binary search tree is a binary tree where elements are sorted in order that all lift children are smaller that the root,
 * and the right children are greater than the root
 * <p>
 * 20 (Root)
 * /       \ (Edge)
 * 10        30
 * /  \      /  \
 * 5   15    25   35 (Leafs)
 * <p>
 * Use cases :
 * representing hierarchical data like in organisation
 * compiler : stack of tree for syntaxe checking
 * autocomplete
 * <p>
 * Performance : are better than arrays & linkedList
 * find : O(log n)
 * insert : O(log n)
 * delete : O(log n)
 * <p>
 * Traversing : 2 categories
 * Breadth First (level order) : visitin levels one by one ==> 20,10, 30, 5, 15, 25, 30
 * Depth First : pre-order, in-order, post-order
 * pre-order : root, left, right ==> 20, 10, 5, 15, 30, 25, 35
 * in-order : left, root, right (result is in ascending order) ==> 5, 10, 15, 20, 25, 30, 35
 * post-order : left, right, root ==> 5, 15, 10, 25, 35, 20
 *
 * @param <T>
 */
public class BinaryTree<T> {

    private Node<T> root;
    private final Comparator<T> comparator;

    public BinaryTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /*
     * We can use sample implementation with infinit while(true) and current Node as local value
     *
     */
    public void add(T value) {
        final Node<T> newNode = new Node<>(value);
        if (Objects.isNull(root)) {
            root = newNode;
            return;
        }
        add(root, newNode);
    }

    public void add(Node<T> root, Node<T> newNode) {
        if (compareNodes(root, newNode) > 0) {
            if (root.leftChild == null) {
                root.leftChild = newNode;
                return;
            }
            add(root.leftChild, newNode);
        } else if (compareNodes(root, newNode) < 0) {
            if (root.rightChild == null) {
                root.rightChild = newNode;
                return;
            }
            add(root.rightChild, newNode);
        }
    }

    public boolean find(T value) {
        Node<T> current = root;
        while (current != null) {
            if (compareNodes(current.value, value) < 0)
                current = current.rightChild;
            if (compareNodes(current.value, value) > 0)
                current = current.leftChild;
            else return true;
        }
        return false;
    }

    public boolean findWithRecursive(T value) {
        return root != null && findWithRecursive(root, value);
    }

    public boolean findWithRecursive(Node<T> currentNode, T value) {
        if (currentNode == null)
            return false;
        return compareNodes(currentNode.value, value) == 0
                || findWithRecursive(compareNodes(currentNode.value, value) > 0 ? currentNode.leftChild : currentNode.rightChild, value);
    }

    private int compareNodes(T currentValue, T newValue) {
        return comparator.compare(currentValue, newValue);
    }

    private int compareNodes(Node<T> currentNode, Node<T> newNode) {
        return compareNodes(currentNode.value, newNode.value);
    }

    public void printWithPreOrderStrategy() {
        System.out.println("#Depth First :: Pre-order strategy");
        List<String> res = new ArrayList<>();
        printWithPreOrderStrategy(root, res);
        System.out.println(res.stream().collect(Collectors.joining(",", "[", "]")));
    }

    private void printWithPreOrderStrategy(Node<T> currentNode, List<String> res) {
        if (currentNode == null)
            return;
        res.add(currentNode.value.toString());
        if (currentNode.leftChild != null)
            printWithPreOrderStrategy(currentNode.leftChild, res);
        if (currentNode.rightChild != null)
            printWithPreOrderStrategy(currentNode.rightChild, res);
    }

    public void printWithInOrderStrategy() {
        System.out.println("#Depth First :: In-order strategy");
        List<String> res = new ArrayList<>();
        printWithInOrderStrategy(root, res);
        System.out.println(res.stream().collect(Collectors.joining(",", "[", "]")));
    }

    private void printWithInOrderStrategy(Node<T> currentNode, List<String> res) {
        if (currentNode == null)
            return;
        if (currentNode.leftChild != null)
            printWithInOrderStrategy(currentNode.leftChild, res);
        res.add(currentNode.value.toString());
        if (currentNode.rightChild != null)
            printWithInOrderStrategy(currentNode.rightChild, res);
    }

    public void printWithPostOrderStrategy() {
        System.out.println("#Depth First :: Post-order strategy");
        List<String> res = new ArrayList<>();
        printWithPostOrderStrategy(root, res);
        System.out.println(res.stream().collect(Collectors.joining(",", "[", "]")));
    }

    private void printWithPostOrderStrategy(Node<T> currentNode, List<String> res) {
        if (currentNode == null)
            return;
        if (currentNode.leftChild != null)
            printWithPostOrderStrategy(currentNode.leftChild, res);
        if (currentNode.rightChild != null)
            printWithPostOrderStrategy(currentNode.rightChild, res);
        res.add(currentNode.value.toString());
    }

    public int height() {
        return height(root);
    }

    private int height(Node<T> root) {
        if (root == null)
            return -1;
        if (isLeaf(root))
            return 0;
        return 1 + Math.max(height(root.leftChild), height(root.rightChild));
    }

    private boolean isLeaf(Node<T> node) {
        return node.rightChild == null && node.leftChild == null;
    }

    /*
     * the min in a binary search tree is the extrem left edge
     *
     * This method looks for min a binary tree (not search binary tree)
     *
     */
    public T min() {
        if (root == null)
            throw new IllegalStateException();
        return min(root);
    }

    public T min(Node<T> node) {
        if (isLeaf(node))
            return node.value;
        T minLeft = node.leftChild != null ? min(node.leftChild) : node.value;
        T minRight = node.rightChild != null ? min(node.rightChild) : node.value;
        T min = compareNodes(minLeft, minRight) > 0 ? minRight : minLeft;
        return compareNodes(min, node.value) > 0 ? node.value : min;
    }

    public T minOfBinarySearchTree() {
        if (root == null)
            throw new IllegalStateException();
        Node<T> current = root;
        Node<T> min = current;
        while (current != null) {
            min = current;
            current = current.leftChild;
        }
        return min.value;
    }

    public boolean equals(BinaryTree<T> tree) {
        return equals(root, tree.root);
    }

    private boolean equals(Node<T> first, Node<T> second) {
        if (first == null && second == null)
            return true;
        if (first != null && second != null)
            return compareNodes(first, second) == 0
                    && equals(first.leftChild, second.leftChild)
                    && equals(first.rightChild, second.rightChild);

        return false;
    }

    public boolean isValidBinarySearchTree() {
        return isValidBinarySearchTree(root, root.value, root.value);
    }

    private boolean isValidBinarySearchTree(Node<T> node, T min, T max) {
        if (node == null)
            return true;

        T curerntMin = min == null ? null : node.value;
        T curerntMax = max == null ? null : node.value;
        if (compareNodes(node.value, curerntMin) < 0 || compareNodes(node.value, curerntMax) > 0)
            return false;

        return isValidBinarySearchTree(node.leftChild, curerntMin, node.value)
                && isValidBinarySearchTree(node.rightChild, node.value, curerntMax);
    }

    public List<T> nodeAtDistance(int distance) {
        List<T> res = new ArrayList<>();
        nodeAtDistance(root, distance, res);
        return res;
    }

    private void nodeAtDistance(Node<T> node, int distance, List<T> res) {
        if (node == null)
            return;

        if (distance == 0) {
            res.add(node.value);
            return;
        }

        nodeAtDistance(node.leftChild, distance - 1, res);
        nodeAtDistance(node.rightChild, distance - 1, res);
    }

    public void printWithLevelOrderStrategy() {
        List<T> res = new ArrayList<>();
        for (int i = 0; i <= height(); i++) {
            res.addAll(nodeAtDistance(i));
        }
        System.out.println(res.stream().map(Object::toString).collect(Collectors.joining(",", "[", "]")));
    }

    private static class Node<T> {
        private final T value;
        private Node<T> leftChild;
        private Node<T> rightChild;

        public Node(T value) {
            this.value = value;
        }
    }
}

class BinaryTreeTest {
    public static void main(String[] args) {
        stringBinaryTreeExample();
        inetegerBinaryTreeExample();
    }

    private static void stringBinaryTreeExample() {
        System.out.println("============== String Binary Tree ==============");
        final BinaryTree<String> stringBinaryTree = new BinaryTree<>(String::compareTo);
        stringBinaryTree.add("M");
        stringBinaryTree.add("F");
        stringBinaryTree.add("A");
        stringBinaryTree.add("Q");
        stringBinaryTree.add("W");
        stringBinaryTree.add("N");
        stringBinaryTree.add("O");
        System.out.println(stringBinaryTree.findWithRecursive("W") + " _ " + stringBinaryTree.find("W"));
        System.out.println(stringBinaryTree.findWithRecursive("F") + " _ " + stringBinaryTree.find("F"));
        System.out.println(stringBinaryTree.findWithRecursive("R") + " _ " + stringBinaryTree.find("R"));
        stringBinaryTree.printWithPreOrderStrategy();
        stringBinaryTree.printWithInOrderStrategy();
        stringBinaryTree.printWithPostOrderStrategy();
        System.out.println("Height : " + stringBinaryTree.height());
        System.out.println("Min : " + stringBinaryTree.min() + " _ " + stringBinaryTree.minOfBinarySearchTree());

        final BinaryTree<String> secondStringBinaryTree = new BinaryTree<>(String::compareTo);
        secondStringBinaryTree.add("M");
        secondStringBinaryTree.add("F");
        secondStringBinaryTree.add("A");
        secondStringBinaryTree.add("Q");
        System.out.println("Equals : " + stringBinaryTree.equals(stringBinaryTree));
        System.out.println("Equals : " + stringBinaryTree.equals(secondStringBinaryTree));
        System.out.println("Is a valide Binary search Tree : " + stringBinaryTree.isValidBinarySearchTree());
        for (int i = 0; i <= 5; i++) {
            System.out.println("Nodes at distance : " + i + " : " + stringBinaryTree.nodeAtDistance(i).stream().map(Object::toString).collect(Collectors.joining(",", "[", "]")));
        }
        stringBinaryTree.printWithLevelOrderStrategy();
    }

    private static void inetegerBinaryTreeExample() {
        System.out.println("============== Integer Binary Tree ==============");
        final BinaryTree<Integer> integerBinaryTree = new BinaryTree<>(Comparator.comparingInt(i -> i));
        integerBinaryTree.add(20);
        integerBinaryTree.add(10);
        integerBinaryTree.add(30);
        integerBinaryTree.add(15);
        integerBinaryTree.add(8);
        integerBinaryTree.add(25);
        integerBinaryTree.add(28);
        System.out.println(integerBinaryTree.findWithRecursive(30) + " _ " + integerBinaryTree.find(30));
        System.out.println(integerBinaryTree.findWithRecursive(25) + " _ " + integerBinaryTree.find(25));
        System.out.println(integerBinaryTree.findWithRecursive(99) + " _ " + integerBinaryTree.find(99));
        integerBinaryTree.printWithPreOrderStrategy();
        integerBinaryTree.printWithInOrderStrategy();
        integerBinaryTree.printWithPostOrderStrategy();
        System.out.println("Height : " + integerBinaryTree.height());
        System.out.println("Min : " + integerBinaryTree.min() + " _ " + integerBinaryTree.minOfBinarySearchTree());

        final BinaryTree<Integer> secondIntegerBinaryTree = new BinaryTree<>(Comparator.comparingInt(i -> i));
        secondIntegerBinaryTree.add(20);
        secondIntegerBinaryTree.add(10);
        secondIntegerBinaryTree.add(30);
        secondIntegerBinaryTree.add(15);
        secondIntegerBinaryTree.add(8);
        System.out.println("Equals : " + integerBinaryTree.equals(integerBinaryTree));
        System.out.println("Equals : " + integerBinaryTree.equals(secondIntegerBinaryTree));
        System.out.println("Is valid Binary searsh tree : " + integerBinaryTree.isValidBinarySearchTree());
        for (int i = 0; i <= 4; i++) {
            System.out.println("Nodes at distance : "  + i + " : " + integerBinaryTree.nodeAtDistance(i).stream().map(Object::toString).collect(Collectors.joining(",", "[", "]")));
        }
        integerBinaryTree.printWithLevelOrderStrategy();
    }
}