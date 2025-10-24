## Assignment 3 — Minimum Spanning Tree (MST) Optimization
# Akbope Bakytkeldy | SE-2426
## Overview
This project implements and compares **Prim’s** and **Kruskal’s** algorithms for finding the **Minimum Spanning Tree (MST)** of a weighted graph.  
Both algorithms were implemented in **Java** using only the standard library 
The goal was to measure performance differences, operation counts, and MST cost consistency across different graph sizes

## Datasets
| Dataset | Graphs | Vertices (V) | Edges (E) | Description |
|----------|--------|--------------|------------|--------------|
| small | 5 | 4–6 | 6–15 | Simple graphs for correctness check |
| medium | 10 | 10–15 | 33–105 | Moderate graphs for timing comparison |
| large | 10 | 20–28 | 190–378 | Dense graphs for performance testing |
| extralarge | 3 | 55–87 | 1298–1780 | Heavy tests for scalability |

Each graph was processed by both algorithms.  
Execution time, operation counts, and MST results were stored in JSON and summarized in `performance_summary.csv`

## Average Execution Time (ns)

| Dataset | Prim | Kruskal |
|----------|------|----------|
| small | 10,766 | 7,210 |
| medium | 74,227 | 61,937 |
| large | 30,902 | 81,269 |
| extralarge | 150,087 | 272,431 |

## Algorithm Comparison

| Algorithm | Time Complexity | Space | Key Idea |
|------------|-----------------|--------|-----------|
| **Prim** | O(E log V) | O(V + E) | Grows tree from a start vertex using a min-heap |
| **Kruskal** | O(E log E) ≈ O(E log V) | O(E + V) | Sorts edges by weight and joins components using DSU |

## Observations from Experiment

**1. Small & Medium graphs**  
Kruskal was slightly faster because sorting a small edge list is cheap and the DSU operations are lightweight.

**2. Large & ExtraLarge graphs**  
Prim became faster. Kruskal’s edge sorting (`E log E`) dominates runtime when the graph is dense. Prim’s heap operations scale better when E is large.

**3. MST cost**  
Both algorithms always produced identical MST total cost, confirming correctness.

## Implementation Notes
- **Measured values:**
    - MST total cost
    - Operation counts:
        - Prim → `heapPush`, `heapPop`, `edgeChecks`
        - Kruskal → `comparisons`, `findOps`, `unionOps`
    - Execution time (averaged in nanoseconds)

## Results Summary

| Graph Type | Faster Algorithm | Reason |
|-------------|------------------|---------|
| Sparse (E close to V) | **Kruskal** | Sorting cost small, DSU efficient |
| Dense (E much larger than V) | **Prim** | Avoids sorting all edges |
| Large networks | **Prim** | Scales better with high edge count |
| Real-world road-like graphs | **Kruskal** | Road networks are usually sparse |

**Key takeaway:**  
Both algorithms produce the same MST, but Prim scales better when graphs get large and dense.  
Kruskal remains simpler and slightly faster for small, sparse datasets.

## File Structure

src/main/java/mst/

├── DataGenerator.java 
├── DataLoader.java
├── Edge.java
├── Graph.java
├── GraphVisualizer.java
├── KruskalAlgorithm.java
├── Main.java 
├── MSTResult.java
├── PrimAlgorithm.java
└── ResultsAnalyzer.java 


Output:
- `output_small.json`
- `output_medium.json`
- `output_large.json`
- `output_extralarge.json`
- `performance_summary.csv`

---

## References

1. [GeeksforGeeks — Prim’s Algorithm (with Priority Queue)](https://www.geeksforgeeks.org/prims-algorithm-using-priority-queue-for-graph-representation/)
2. [GeeksforGeeks — Kruskal’s Algorithm (Union-Find Implementation)](https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/)
3. [Wikipedia — Minimum Spanning Tree Overview](https://en.wikipedia.org/wiki/Minimum_spanning_tree)
5. Java Documentation

## Final Conclusion
> Both Prim’s and Kruskal’s algorithms were successfully implemented, tested, and benchmarked.  
> Kruskal performs better for small, sparse graphs; Prim becomes superior for large or dense graphs.  
> Experimental data matches the theoretical expectations for MST algorithms.
