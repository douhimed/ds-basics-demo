package datastructure.advanceds.graphs;

import datastructure.advanceds.NamesUtils;

import java.util.*;
import java.util.stream.Stream;

public class GraphExample {

    public static final Stream<String[]> EDGES = Stream.of("Med,Ahmed", "Med,Khalid", "Med,Hicham", "Khalid,Hicham", "Hicham,Ahmed", "Hicham,Marouane")
            .map(edge -> edge.split(","));

    public GraphExample() {
        System.out.println("===========================[ NON GENERIC GRAPH ]==============================");
        exp01();
        System.out.println("===========================[ GENERIC GRAPH ]==============================");
        exp02();
    }

    private void exp02() {
        final GenericGraph graph = new GenericGraph();
        addVertices(graph);
        addEdges(graph);

        System.out.println(graph);
        System.out.println(graph.getVertices(new Vertex(NamesUtils.MED)));

        removeVertex(graph, NamesUtils.AHMED);
        System.out.println(graph);
        System.out.println(graph.getVertices(new Vertex(NamesUtils.MED)));

        System.out.println(graph.depthFirstTraversal(new Vertex(NamesUtils.MED)));
        System.out.println(graph.breadthFirstTraversal(new Vertex(NamesUtils.MED)));

        System.out.println("hasVertex : " + graph.hasVertex(new Vertex(NamesUtils.MED)));
        System.out.println("hasVertex : " + graph.hasVertex(new Vertex(NamesUtils.AHMED)));

        System.out.println("has edge : " + graph.hasEdge(new Vertex(NamesUtils.MED), new Vertex(NamesUtils.HICHAM)));
        System.out.println("has edge : " + graph.hasEdge(new Vertex(NamesUtils.MED), new Vertex(NamesUtils.AHMED))); // Ahmed does not exist
        System.out.println("has edge : " + graph.hasEdge(new Vertex(NamesUtils.MED), new Vertex("Marouane")));
    }

    private void removeVertex(GenericGraph graph, String label) {
        graph.removeVertex(new Vertex(label));
    }

    private void addVertices(GenericGraph graph) {
        NamesUtils.NAMES.forEach(v -> graph.addVertex(new Vertex(v)));
    }

    private static void addEdges(GenericGraph graph) {
        EDGES.forEach(keys -> graph.addEdge(new Vertex(keys[0]), new Vertex(keys[1])));
    }

    private void exp01() {

        final Graph graph = new Graph();
        addVertices(graph);
        addEdges(graph);

        System.out.println(graph);
        System.out.println(graph.getVertices(NamesUtils.MED));

        removeVertex(graph, NamesUtils.AHMED);
        System.out.println(graph);
        System.out.println(graph.getVertices(NamesUtils.MED));

        System.out.println(graph.depthFirstTraversal(NamesUtils.MED));
        System.out.println(graph.breadthFirstTraversal(NamesUtils.MED));

        System.out.println("hasVertex : " + graph.hasVertex(NamesUtils.MED));
        System.out.println("hasVertex : " + graph.hasVertex(NamesUtils.AHMED));

        System.out.println("has edge : " + graph.hasEdge(NamesUtils.MED, NamesUtils.HICHAM));
        System.out.println("has edge : " + graph.hasEdge(NamesUtils.MED, NamesUtils.AHMED)); // Ahmed does not exist
        System.out.println("has edge : " + graph.hasEdge(NamesUtils.MED, "Marouane"));
    }

    private void removeVertex(Graph graph, String label) {
        graph.removeVertex(label);
    }

    private static void addEdges(Graph graph) {
        EDGES.forEach(keys -> graph.addEdge(keys[0], keys[1]));
    }

    private static void addVertices(Graph graph) {
        NamesUtils.NAMES.forEach(graph::addVertex);
    }

}

class GenericGraph<T extends GraphMarker> {

    private final Map<T, List<T>> vertices = new HashMap<>();

    void addVertex(T vertex) {
        vertices.putIfAbsent(vertex, new ArrayList<>());
    }

    void removeVertex(T vertex) {
        vertices.values().forEach(v -> v.remove(vertex));
        vertices.remove(vertex);
    }

    void addEdge(T v1, T v2) {
        addEdge(v1, v2, false);
    }

    void addEdge(T v1, T v2, boolean isBidirectional) {

        isStringBlank("Vertex label must not be blank", v1.getLabel(), v1.getLabel());
        validateSelfLoops(v1.getLabel(), v2.getLabel());

        doesVertexExist(v1, true);
        doesVertexExist(v2, true);

        vertices.get(v1).add(v2);
        if (isBidirectional)
            vertices.get(v2).add(v1);
    }

    public List<T> getVertices(T vertex) {
        return vertices.get(vertex);
    }

    private boolean doesVertexExist(T vertex, boolean throwable) {
        boolean isExist = vertices.containsKey(vertex);
        if (!isExist && throwable)
            throw new IllegalStateException("Vertex not exist");
        return isExist;
    }

    public Set<String> depthFirstTraversal(T root) {
        isStringBlank("Root must not be blank", root.getLabel());
        doesVertexExist(root, true);

        final Set<String> visited = new LinkedHashSet<>();
        final Stack<T> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            T vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex.getLabel());
                for (T v : getVertices(vertex)) {
                    stack.push(v);
                }
            }
        }
        return visited;
    }

    public Set<String> breadthFirstTraversal(T root) {
        isStringBlank("Root must not be blank", root.getLabel());
        doesVertexExist(root, true);

        final Set<String> visited = new LinkedHashSet<>();
        final Queue<T> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root.getLabel());

        while (!queue.isEmpty()) {
            T vertex = queue.poll();
            for (T v : getVertices(vertex)) {
                if (!visited.contains(v.getLabel())) {
                    visited.add(v.getLabel());
                    queue.add(v);
                }
            }
        }

        return visited;
    }

    public boolean hasVertex(Vertex vertex) {
        return vertices.containsKey(vertex);
    }

    public boolean hasEdge(Vertex v1, Vertex v2) {
        isStringBlank("vertex labels must not be blank", v1.getLabel(), v2.getLabel());

        if (!(hasVertex(v1) && hasVertex(v2)))
            return false;

        return vertices.get(v1).contains(v2) || vertices.get(v2).contains(v1);
    }

    private void isStringBlank(String message, String... labels) {
        for (String label : labels)
            if (Objects.isNull(label) || label.trim().length() == 0)
                throw new IllegalStateException(message);
    }

    private static void validateSelfLoops(String label1, String label2) {
        if (label1.equals(label2))
            throw new IllegalStateException("Cannot add edge for the same vertex, no self loops supported in this example");
    }

    @Override
    public String toString() {
        return "Graph : " + vertices;
    }

}

class Graph {

    private final Map<Vertex, List<Vertex>> vertices = new HashMap<>();

    void addVertex(String label) {
        vertices.putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    void removeVertex(String label) {
        Vertex vertex = new Vertex(label);
        vertices.values().forEach(v -> v.remove(vertex));
        vertices.remove(vertex);
    }

    void addEdge(String label1, String label2) {
        addEdge(label1, label2, false);
    }

    void addEdge(String label1, String label2, boolean isBidirectional) {

        isStringBlank("Vertex label must not be blank", label1, label2);
        validateSelfLoops(label1, label2);

        final Vertex v1 = new Vertex(label1);
        doesVertexExist(v1, true);

        final Vertex v2 = new Vertex(label2);
        doesVertexExist(v2, true);

        vertices.get(v1).add(v2);
        if (isBidirectional)
            vertices.get(v2).add(v1);
    }

    public List<Vertex> getVertices(String label) {
        return vertices.get(new Vertex(label));
    }

    public Set<String> depthFirstTraversal(String root) {
        isStringBlank("Root must not be blank", root);
        doesVertexExist(new Vertex(root), true);

        final Set<String> visited = new LinkedHashSet<>();
        final Stack<String> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            String vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Vertex v : getVertices(vertex)) {
                    stack.push(v.getLabel());
                }
            }
        }
        return visited;
    }

    public Set<String> breadthFirstTraversal(String root) {
        isStringBlank("Root must not be blank", root);
        doesVertexExist(new Vertex(root), true);

        final Set<String> visited = new LinkedHashSet<String>();
        final Queue<String> queue = new LinkedList<String>();
        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            for (Vertex v : getVertices(vertex)) {
                if (!visited.contains(v.getLabel())) {
                    visited.add(v.getLabel());
                    queue.add(v.getLabel());
                }
            }
        }

        return visited;
    }

    public boolean hasVertex(String label) {
        return vertices.containsKey(new Vertex(label));
    }

    public boolean hasEdge(String label1, String label2) {
        isStringBlank("vertex labels must not be blank", label1, label2);

        if (!(hasVertex(label1) && hasVertex(label2)))
            return false;

        final Vertex v1 = new Vertex(label1);
        final Vertex v2 = new Vertex(label2);
        return vertices.get(v1).contains(v2) || vertices.get(v2).contains(v1);
    }

    @Override
    public String toString() {
        return "Graph : " + vertices;
    }

    private void isStringBlank(String message, String... labels) {
        for (String label : labels)
            if (Objects.isNull(label) || label.trim().length() == 0)
                throw new IllegalStateException(message);
    }

    private static void validateSelfLoops(String label1, String label2) {
        if (label1.equals(label2))
            throw new IllegalStateException("Cannot add edge for the same vertex, no self loops supported in this example");
    }

    private boolean doesVertexExist(Vertex vertex, boolean throwable) {
        boolean isExist = vertices.containsKey(vertex);
        if (!isExist && throwable)
            throw new IllegalStateException("Vertex not exist");
        return isExist;
    }

}

interface GraphMarker {
    String getLabel();
}

class Vertex implements GraphMarker {
    private String label;

    public Vertex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(label, vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return "{" + label + "}";
    }
}
