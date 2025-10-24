package mst;
import java.util.*;

public class KruskalAlgorithm {

    private static class DSU {
        int[] parent, rank;
        int findOps = 0;
        int unionOps = 0;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            findOps++;
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int a, int b) {
            unionOps++;
            int ra = find(a);
            int rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) {
                parent[ra] = rb;
            } else if (rank[ra] > rank[rb]) {
                parent[rb] = ra;
            } else {
                parent[rb] = ra;
                rank[ra]++;
            }
            return true;
        }
    }

    public static MSTResult runKruskal(Graph graph) {
        int REPEAT = 1000;
        long totalNs = 0;

        MSTResult firstResult = runOnce(graph);

        for (int i = 0; i < REPEAT; i++) {
            long start = System.nanoTime();
            runOnce(graph);
            long end = System.nanoTime();
            totalNs += (end - start);
        }

        double avgNs = (double) totalNs / REPEAT;

        return new MSTResult("Kruskal", firstResult.getMstEdges(), firstResult.getTotalCost(),
                firstResult.getVertexCount(), firstResult.getEdgeCount(),
                firstResult.getOperations(), (long) avgNs);
    }

    private static MSTResult runOnce(Graph graph) {
        int V = graph.getVertices();
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);

        DSU dsu = new DSU(V);
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        int comparisons = 0;

        for (Edge e : edges) {
            comparisons++;
            int u = e.getSrc();
            int v = e.getDest();

            if (dsu.find(u) != dsu.find(v)) {
                if (dsu.union(u, v)) {
                    mstEdges.add(e);
                    totalCost += e.getWeight();
                }
            }

            if (mstEdges.size() == V - 1)
                break;
        }

        Map<String, Integer> ops = new HashMap<>();
        ops.put("comparisons", comparisons);
        ops.put("findOps", dsu.findOps);
        ops.put("unionOps", dsu.unionOps);

        return new MSTResult("Kruskal", mstEdges, totalCost, V, graph.getEdges().size(), ops, 0);
    }
}