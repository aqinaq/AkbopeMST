package mst;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class MSTTest {

    private List<Graph> graphs;

    @BeforeEach
    public void setup() throws Exception {
        graphs = DataLoader.loadGraphs("src/main/resources/input_small.json");
    }

    @Test
    public void testMSTCorrectness() {
        for (Graph g : graphs) {
            MSTResult prim = PrimAlgorithm.runPrim(g, 0);
            MSTResult kruskal = KruskalAlgorithm.runKruskal(g);

            assertEquals(prim.getTotalCost(), kruskal.getTotalCost(),
                    "MST total cost should be identical");

            int expectedEdges = g.getVertices() - 1;
            assertEquals(expectedEdges, prim.getMstEdges().size(),
                    "Prim MST should have V-1 edges");
            assertEquals(expectedEdges, kruskal.getMstEdges().size(),
                    "Kruskal MST should have V-1 edges");

            assertTrue(isAcyclic(prim.getMstEdges(), g.getVertices()), "Prim MST must be acyclic");
            assertTrue(isAcyclic(kruskal.getMstEdges(), g.getVertices()), "Kruskal MST must be acyclic");

            assertTrue(isConnected(prim.getMstEdges(), g.getVertices()), "Prim MST must be connected");
            assertTrue(isConnected(kruskal.getMstEdges(), g.getVertices()), "Kruskal MST must be connected");

            assertTrue(prim.getTimeNs() >= 0, "Prim execution time must be ≥ 0");
            assertTrue(kruskal.getTimeNs() >= 0, "Kruskal execution time must be ≥ 0");

            prim.getOperations().values().forEach(v -> assertTrue(v >= 0));
            kruskal.getOperations().values().forEach(v -> assertTrue(v >= 0));
        }
    }

    @Test
    public void testDisconnectedGraphHandling() {

        Graph g = new Graph(4);
        g.addEdge(0, 1, 5);
        g.addEdge(2, 3, 10);

        MSTResult prim = PrimAlgorithm.runPrim(g, 0);
        MSTResult kruskal = KruskalAlgorithm.runKruskal(g);

        assertTrue(prim.getMstEdges().size() < g.getVertices() - 1, "Prim should handle disconnected graph gracefully");
        assertTrue(kruskal.getMstEdges().size() < g.getVertices() - 1, "Kruskal should handle disconnected graph gracefully");
    }

    private boolean isAcyclic(List<Edge> edges, int V) {
        int[] parent = new int[V];
        for (int i = 0; i < V; i++) parent[i] = i;

        for (Edge e : edges) {
            int u = find(parent, e.getSrc());
            int v = find(parent, e.getDest());
            if (u == v) return false; // cycle detected
            parent[u] = v;
        }
        return true;
    }

    private int find(int[] parent, int x) {
        if (parent[x] != x) parent[x] = find(parent, parent[x]);
        return parent[x];
    }

    private boolean isConnected(List<Edge> edges, int V) {
        boolean[] visited = new boolean[V];
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int i = 0; i < V; i++) adj.put(i, new ArrayList<>());
        for (Edge e : edges) {
            adj.get(e.getSrc()).add(e.getDest());
            adj.get(e.getDest()).add(e.getSrc());
        }

        dfs(0, adj, visited);
        for (boolean v : visited) if (!v) return false;
        return true;
    }
    private void dfs(int node, Map<Integer, List<Integer>> adj, boolean[] visited) {
        visited[node] = true;
        for (int nei : adj.get(node)) {
            if (!visited[nei]) dfs(nei, adj, visited);
        }
    }
}
