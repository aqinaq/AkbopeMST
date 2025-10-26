# Assignment 3 — Minimum Spanning Tree (MST) Optimization  
## Akbope Bakytkeldy | SE-2426  

## Overview
This project implements and compares **Prim’s** and **Kruskal’s** algorithms for finding the **Minimum Spanning Tree (MST)** of a weighted graph.  
Both algorithms were implemented in **Java** using only the standard library.  
The goal was to evaluate performance, operation counts, and MST cost consistency across various graph sizes.

## Datasets

| Dataset | Graphs | Vertices (Range) | Edges (Range) | Description |
|----------|--------|------------------|----------------|--------------|
| small | 5 | 7–20 | 11–65 | Small graphs for correctness and baseline testing |
| medium | 10 | 30–292 | 369–993 | Moderate-size graphs for timing and scalability analysis |
| large | 10 | 326–995 | 915–2785 | Dense graphs for stress and performance testing |
| extralarge | 3 | 1400–2712 | 3870–6815 | Very large graphs for scalability and limit evaluation |

Each graph was processed by both algorithms.  
Execution time, operation counts, and MST results were saved in `performance_summary.csv`.

## Average Execution Time (ns)

| Dataset | Prim (avg ns) | Kruskal (avg ns) |
|----------|----------------|------------------|
| small | **11,454** | **13,996** |
| medium | **153,844** | **216,968** |
| large | **397,404** | **395,214** |
| extralarge | **1,347,135** | **999,762** |

> Prim’s algorithm outperformed Kruskal’s on large, dense graphs, while Kruskal remained competitive on small and sparse datasets.

## Algorithm Comparison

| Algorithm | Time Complexity | Space Complexity | Core Idea |
|------------|-----------------|------------------|------------|
| **Prim** | O(E log V) | O(V + E) | Expands the MST from a single vertex using a min-heap (priority queue). |
| **Kruskal** | O(E log E) ≈ O(E log V) | O(E + V) | Sorts all edges by weight and joins components using Disjoint Set Union (Union-Find). |

## Observations

- **Small graphs:**  
  Both algorithms produced identical MST costs. Kruskal’s DSU overhead made it slightly slower.

- **Medium graphs:**  
  Prim became faster as edge density increased, since heap operations scale well with E.

- **Large graphs:**  
  Both algorithms performed similarly, but Prim showed steadier execution under dense conditions.

- **ExtraLarge graphs:**  
  Prim handled the biggest graphs more efficiently, while Kruskal’s heavy sorting and DSU calls increased total time.

- **MST Cost Consistency:**  
  Prim and Kruskal always produced identical MST costs, confirming algorithmic correctness.
## Implementation Notes

**Measured parameters:**
- MST total cost  
- Operation counts:  
  - *Prim:* `heapPush`, `heapPop`, `edgeChecks`  
  - *Kruskal:* `comparisons`, `findOps`, `unionOps`  
- Execution time (nanoseconds)

All results were automatically generated and analyzed from:
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

**Output files:**
- `output_small.json`
- `output_medium.json`
- `output_large.json`
- `output_extralarge.json`
- `performance_summary.csv`

## Results Summary

| Graph Type | Faster Algorithm | Explanation |
|-------------|------------------|--------------|
| Sparse (E ≈ V) | **Kruskal** | Sorting overhead is small; DSU efficient |
| Dense (E ≫ V) | **Prim** | Avoids sorting all edges; better heap scaling |
| Large networks | **Prim** | Scales better with high edge count |
| Road-like networks | **Kruskal** | Road graphs are generally sparse and tree-like |

**Conclusion:**  
Both algorithms produce the same MST cost.  
**Prim’s algorithm scales better** for dense and large graphs, while **Kruskal’s simplicity** makes it ideal for small or sparse graphs.

---
## References

1. [GeeksforGeeks — Prim’s Algorithm (Priority Queue)](https://www.geeksforgeeks.org/prims-algorithm-using-priority-queue-for-graph-representation/)  
2. [GeeksforGeeks — Kruskal’s Algorithm (Union-Find)](https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/)  
3. [Wikipedia — Minimum Spanning Tree](https://en.wikipedia.org/wiki/Minimum_spanning_tree)  
4. Java SE Documentation  

---
## Final Conclusion 
> Both Prim’s and Kruskal’s algorithms were successfully implemented, tested, and benchmarked.  
> Kruskal performs better for small, sparse graphs; Prim becomes superior for large or dense graphs.  
> Experimental data matches the theoretical expectations for MST algorithms.
