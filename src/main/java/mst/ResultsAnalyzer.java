package mst;
import com.google.gson.*;
import java.io.*;
import java.util.Locale;

public class ResultsAnalyzer {

    public static void main(String[] args) throws IOException {
        String[] files = {
                "src/main/resources/output_small.json",
                "src/main/resources/output_medium.json",
                "src/main/resources/output_large.json",
                "src/main/resources/output_extralarge.json"
        };

        File csvFile = new File("src/main/resources/performance_summary.csv");
        try (PrintWriter pw = new PrintWriter(csvFile)) {

            pw.println("Dataset, Graph_ID, Vertices,Edges, "
                    + "Prim_Cost ,Prim_heapPush ,Prim_heapPop ,Prim_edgeChecks ,Prim_Time_ns ,"
                    + "Kruskal_Cost ,Kruskal_comparisons ,Kruskal_findOps ,Kruskal_unionOps ,Kruskal_Time_ns ");

            for (String path : files) {
                String dataset = path.substring(path.indexOf("output_") + 7, path.indexOf(".json"));
                analyzeDataset(path, dataset, pw);
            }
        }

        System.out.println("summary written to: src/main/resources/performance_summary.csv");
    }

    private static void analyzeDataset(String path, String dataset, PrintWriter pw) throws IOException {
        try (Reader reader = new FileReader(path)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray results = root.getAsJsonArray("results");

            double primTotalTime = 0;
            double kruskalTotalTime = 0;
            int count = 0;

            for (JsonElement elem : results) {
                JsonObject obj = elem.getAsJsonObject();
                int id = obj.get("graph_id").getAsInt();
                int v = obj.getAsJsonObject("input_stats").get("vertices").getAsInt();
                int e = obj.getAsJsonObject("input_stats").get("edges").getAsInt();

                JsonObject prim = obj.getAsJsonObject("prim");
                JsonObject kruskal = obj.getAsJsonObject("kruskal");

                double primCost = prim.get("total_cost").getAsDouble();
                double kruskalCost = kruskal.get("total_cost").getAsDouble();
                double primTime = prim.get("execution_time_ns").getAsDouble();
                double kruskalTime = kruskal.get("execution_time_ns").getAsDouble();

                JsonObject primOps = prim.getAsJsonObject("operations");
                JsonObject kruskalOps = kruskal.getAsJsonObject("operations");

                int heapPush = primOps.get("heapPush").getAsInt();
                int heapPop = primOps.get("heapPop").getAsInt();
                int edgeChecks = primOps.get("edgeChecks").getAsInt();

                int comparisons = kruskalOps.get("comparisons").getAsInt();
                int findOps = kruskalOps.get("findOps").getAsInt();
                int unionOps = kruskalOps.get("unionOps").getAsInt();

                primTotalTime += primTime;
                kruskalTotalTime += kruskalTime;
                count++;

                pw.printf(Locale.US,
                        "%s,%d,%d,%d,%.2f,%d,%d,%d,%.3f,%.2f,%d,%d,%d,%.3f%n",
                        dataset, id , v , e ,
                        primCost , heapPush , heapPop , edgeChecks , primTime ,
                        kruskalCost , comparisons , findOps , unionOps, kruskalTime);
            }

            System.out.printf("%s → %d graphs, Avg Prim=%.3f ns, Avg Kruskal=%.3f ns%n",
                    dataset, count, primTotalTime / count, kruskalTotalTime / count);
        }
    }
}
