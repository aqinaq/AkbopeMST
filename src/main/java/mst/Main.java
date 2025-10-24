package mst;
import com.google.gson.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        String[] inputFiles = {
                "src/main/resources/input_small.json",
                "src/main/resources/input_medium.json",
                "src/main/resources/input_large.json",
                "src/main/resources/input_extralarge.json"
        };

        for (String inputFile : inputFiles) {
            String outputFile = inputFile.replace("input", "output");

            System.out.println("Processing dataset: " + inputFile);

            List<Graph> graphs = DataLoader.loadGraphs(inputFile);
            JsonArray results = new JsonArray();

            JsonObject root = new JsonObject();
            root.add("results", results);

            try (Writer writer = new FileWriter(outputFile)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(root, writer);
            }

            System.out.println("Results written to: " + outputFile + "\n");
        }

        System.out.println("datasets processed successfully!");
    }

    private static JsonObject buildAlgorithmJson(Graph g, MSTResult result) {
        JsonObject algoJson = new JsonObject();

        JsonArray edgeArray = new JsonArray();
        for (Edge e : result.getMstEdges()) {
            JsonObject edgeJson = new JsonObject();
            edgeJson.addProperty("from", g.getIndexToName().get(e.getSrc()));
            edgeJson.addProperty("to", g.getIndexToName().get(e.getDest()));
            edgeJson.addProperty("weight", e.getWeight());
            edgeArray.add(edgeJson);
        }
        algoJson.add("mst_edges", edgeArray);

        algoJson.addProperty("total_cost", result.getTotalCost());

        JsonObject opsJson = new JsonObject();
        result.getOperations().forEach(opsJson::addProperty);
        algoJson.add("operations", opsJson);

        algoJson.addProperty("execution_time_ns", Math.round(result.getTimeNs() * 100.0) / 100.0);

        return algoJson;
    }
}
