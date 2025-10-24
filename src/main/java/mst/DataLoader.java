package mst;
import com.google.gson.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DataLoader {

    public static List<Graph> loadGraphs(String filePath) throws IOException {
        try (Reader reader = new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8)) {

            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray graphsArray = root.getAsJsonArray("graphs");
            List<Graph> graphs = new ArrayList<>();

            for (JsonElement graphElem : graphsArray) {
                JsonObject gObj = graphElem.getAsJsonObject();
                JsonArray nodesArr = gObj.getAsJsonArray("nodes");
                JsonArray edgesArr = gObj.getAsJsonArray("edges");

                Map<String, Integer> nameToIndex = new LinkedHashMap<>();
                Map<Integer, String> indexToName = new LinkedHashMap<>();
                for (int i = 0; i < nodesArr.size(); i++) {
                    String name = nodesArr.get(i).getAsString();
                    nameToIndex.put(name, i);
                    indexToName.put(i, name);
                }

                Graph graph = new Graph(nodesArr.size());
                for (JsonElement eElem : edgesArr) {
                    JsonObject e = eElem.getAsJsonObject();
                    String from = e.get("from").getAsString();
                    String to = e.get("to").getAsString();
                    int w = e.get("weight").getAsInt();
                    graph.addEdge(nameToIndex.get(from), nameToIndex.get(to), w);
                }

                graph.setNameMap(nameToIndex, indexToName);
                graph.setId(gObj.get("id").getAsInt());
                graphs.add(graph);
            }
            return graphs;
        }
    }
}
