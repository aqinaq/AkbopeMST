package mst;

import java.util.*;

public class Graph {
    private int id;
    private int vertices;
    private List<Edge> edges;
    private Map<Integer, List<Edge>> adjacencyList;
    private Map<String,Integer> nameToIndex;
    private Map<Integer,String> indexToName;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.adjacencyList = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest, int weight) {
        Edge e = new Edge(src, dest, weight);
        edges.add(e);
        adjacencyList.get(src).add(e);
        adjacencyList.get(dest).add(e);
    }
    public int getVertices() { return vertices; }
    public List<Edge> getEdges() { return edges; }
    public Map<Integer, List<Edge>> getAdjacencyList() { return adjacencyList; }

    public void setNameMap(Map<String,Integer> n2i, Map<Integer,String> i2n) {
        this.nameToIndex = n2i;
        this.indexToName = i2n;
    }
    public Map<Integer,String> getIndexToName() { return indexToName; }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
}
