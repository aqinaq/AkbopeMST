package mst;
import java.util.*;

public class PrimAlgorithm {

    public static MSTResult runPrim(Graph graph, int startVertex) {
        int REPEAT = 1000;
        long totalNs = 0;

        MSTResult firstResult = runOnce(graph, startVertex);

        for (int i = 0; i < REPEAT; i++) {
            long start = System.nanoTime();
            runOnce(graph, startVertex);
            long end = System.nanoTime();
            totalNs += (end - start);
        }

        double avgNs = (double) totalNs / REPEAT;

        return new MSTResult("Prim", firstResult.getMstEdges(), firstResult.getTotalCost(),
                firstResult.getVertexCount(), firstResult.getEdgeCount(),
                firstResult.getOperations(), (long) avgNs);
    }

    private static MSTResult runOnce(Graph graph, int startVertex) {
        int V = graph.getVertices();
        Map<Integer, List<Edge>> adj = graph.getAdjacencyList();

        class PQNode implements Comparable<PQNode> {
            int weight, from, to;
            PQNode(int w, int f, int t) { weight = w; from = f; to = t; }
            public int compareTo(PQNode o) { return Integer.compare(this.weight, o.weight); }
        }

        PriorityQueue<PQNode> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[V];
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        int heapPush = 0, heapPop = 0, edgeChecks = 0;

        visited[startVertex] = true;
        for (Edge e : adj.get(startVertex)) {
            int to = (e.getSrc() == startVertex) ? e.getDest() : e.getSrc();
            pq.add(new PQNode(e.getWeight(), startVertex, to));
            heapPush++;
        }

        while (!pq.isEmpty() && mstEdges.size() < V - 1) {
            heapPop++;
            PQNode node = pq.poll();
            edgeChecks++;
            if (visited[node.to]) continue;

            mstEdges.add(new Edge(node.from, node.to, node.weight));
            totalCost += node.weight;
            visited[node.to] = true;

            for (Edge e : adj.get(node.to)) {
                int to = (e.getSrc() == node.to) ? e.getDest() : e.getSrc();
                if (!visited[to]) {
                    pq.add(new PQNode(e.getWeight(), node.to, to));
                    heapPush++;
                }
            }
        }

        Map<String, Integer> ops = new HashMap<>();
        ops.put("heapPush", heapPush);
        ops.put("heapPop", heapPop);
        ops.put("edgeChecks", edgeChecks);

        return new MSTResult("Prim", mstEdges, totalCost, V, graph.getEdges().size(), ops, 0);
    }
}