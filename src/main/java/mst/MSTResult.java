package mst;
import java.util.List;
import java.util.Map;

public class MSTResult {
    private final String algorithm;
    private final List<Edge> mstEdges;
    private final int totalCost;
    private final int nVertices;
    private final int nEdges;
    private final Map<String, Integer> ops;
    private final double timeNs;

    public MSTResult(String algorithm, List<Edge> mstEdges, int totalCost,
                     int nVertices, int nEdges, Map<String,Integer> ops, long timeMs) {
        this.algorithm = algorithm;
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.nVertices = nVertices;
        this.nEdges = nEdges;
        this.ops = ops;
        this.timeNs = timeMs;
    }

    public String getAlgorithm() { return algorithm; }
    public List<Edge> getMstEdges() { return mstEdges; }
    public int getTotalCost() { return totalCost; }
    public int getNVertices() { return nVertices; }
    public int getNEdges() { return nEdges; }
    public Map<String,Integer> getOps() { return ops; }
    public double getTimeNs() { return timeNs; }
    public long getTimeMs() { return (long) timeNs; }
    public int getVertexCount() { return nVertices; }
    public int getEdgeCount() { return nEdges; }
    public Map<String, Integer> getOperations() { return ops; }

    @Override
    public String toString() {
        return algorithm + " MST cost=" + totalCost +
                ", edges=" + mstEdges.size() +
                ", time=" + timeNs + "ms, ops=" + ops;
    }
}