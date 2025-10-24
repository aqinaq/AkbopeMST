package mst;

public class Edge implements Comparable<Edge> {
    private final int src;
    private final int dest;
    private final int weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
    public int getSrc() { return src; }
    public int getDest() { return dest; }
    public int getWeight() { return weight; }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return src + "-" + dest + " (" + weight + ")";
    }
}
