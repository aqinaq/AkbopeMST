package mst;

import com.google.gson.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataGenerator {

    private static final Random rand = new Random();

    public static void main(String[] args) throws IOException {
        generateSmallGraphs("src/main/resources/input_small.json");
        generateMediumGraphs("src/main/resources/input_medium.json");
        generateLargeGraphs("src/main/resources/input_large.json");
        generateExtraLargeGraphs("src/main/resources/input_extralarge.json");

        System.out.println("datasets generated successfully!");
    }

    private static void generateSmallGraphs(String filePath) throws IOException {
        generateGraphs(filePath, 5, 4, 6, 10, 30, 1);
    }

    private static void generateMediumGraphs(String filePath) throws IOException {
        generateGraphs(filePath, 10, 10, 15, 30, 300, 6);
    }

    private static void generateLargeGraphs(String filePath) throws IOException {
        generateGraphs(filePath, 10, 20, 30, 300, 1000, 16);
    }

    private static void generateExtraLargeGraphs(String filePath) throws IOException {
        generateGraphs(filePath, 3, 50, 100, 1000, 2000, 26);
    }

    private static void generateGraphs(String filePath, int numGraphs,
                                       int minVertices, int maxVertices,
                                       int minEdges, int maxEdges,
                                       int startId) throws IOException {
        JsonObject root = new JsonObject();
        JsonArray graphsArray = new JsonArray();

        for (int g = 0; g < numGraphs; g++) {
            int V = randomBetween(minVertices, maxVertices);
            int maxPossibleEdges = V * (V - 1) / 2;
            int E = Math.min(randomBetween(minEdges, maxEdges), maxPossibleEdges);

            JsonObject graphJson = new JsonObject();
            graphJson.addProperty("id", startId + g);

            List<String> nodes = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                nodes.add(indexToName(i));
            }

            JsonArray nodesArray = new JsonArray();
            nodes.forEach(nodesArray::add);
            graphJson.add("nodes", nodesArray);

            Set<String> existingEdges = new HashSet<>();
            JsonArray edgesArray = new JsonArray();

            List<Integer> verticesList = new ArrayList<>();
            for (int i = 0; i < V; i++) verticesList.add(i);
            Collections.shuffle(verticesList);

            for (int i = 1; i < V; i++) {
                int from = verticesList.get(i - 1);
                int to = verticesList.get(i);
                int weight = randomBetween(1, 100);

                String key = from < to ? from + "-" + to : to + "-" + from;
                existingEdges.add(key);

                JsonObject edge = new JsonObject();
                edge.addProperty("from", nodes.get(from));
                edge.addProperty("to", nodes.get(to));
                edge.addProperty("weight", weight);
                edgesArray.add(edge);
            }


            while (edgesArray.size() < E) {
                int u = rand.nextInt(V);
                int v = rand.nextInt(V);
                if (u == v) continue;

                String key = u < v ? u + "-" + v : v + "-" + u;
                if (existingEdges.contains(key)) continue;

                existingEdges.add(key);
                JsonObject edge = new JsonObject();
                edge.addProperty("from", nodes.get(u));
                edge.addProperty("to", nodes.get(v));
                edge.addProperty("weight", randomBetween(1, 100));
                edgesArray.add(edge);
            }

            graphJson.add("edges", edgesArray);
            graphsArray.add(graphJson);
        }

        root.add("graphs", graphsArray);

        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        }

        System.out.println("Generated " + numGraphs + " graphs → " + filePath);
    }

    private static int randomBetween(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    private static String indexToName(int index) {
        StringBuilder name = new StringBuilder();
        while (index >= 0) {
            name.insert(0, (char) ('A' + (index % 26)));
            index = index / 26 - 1;
        }
        return name.toString();
    }
}
