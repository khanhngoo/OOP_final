import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import com.google.gson.Gson;

public class ConsoleApp {
    public static void main(String[] args) throws IOException {
        // 1) Tạo GraphManager và load dữ liệu
        GraphManager gm = new GraphManager();
        loadFromCSV("campus.csv", gm);               // điểm và vị trí
        loadFixedEdges("campus_edges.csv", gm);      // tuyến cố định nếu có

        // 2) Đọc input
        List<String> lines = Files.readAllLines(Paths.get("input.txt"));
        if (lines.size() < 2) {
            System.out.println("⚠️ input.txt cần 2 dòng: start và end");
            return;
        }

        String src = lines.get(0).trim();
        String dst = lines.get(1).trim();
        System.out.println("Java Output: Start: " + src);
        System.out.println("End: " + dst);

        // 3) Chạy thuật toán
        var result = PathfindingEngine.dijkstra(src, dst, gm);
        if (result == null) {
            System.out.println("No route found.");
        } else {
            // In kết quả
            System.out.println("Path: " + String.join(" -> ", result.route));
            System.out.printf("Total distance: %.2f meters%n", result.cost.distance);
            System.out.printf("Total cost: %.2f%n", result.cost.totalCost());

            // Ghi ra file JSON
            String json = new Gson().toJson(result.route);
            Files.write(Paths.get("route.json"), json.getBytes());
        }
    }

    // Đọc tọa độ các điểm từ campus.csv
    static void loadFromCSV(String filename, GraphManager gm) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        Map<String, double[]> coords = new HashMap<>();

        for (int i = 1; i < lines.size(); i++) {
            var cols = lines.get(i).split(",");
            if (cols.length < 4) {
                System.out.println("⚠️ Bỏ qua dòng lỗi: " + lines.get(i));
                continue;
            }

            String id = cols[0].trim();
            double lat = Double.parseDouble(cols[2].trim());
            double lon = Double.parseDouble(cols[3].trim());
            coords.put(id, new double[] { lon, lat }); // Lưu theo [lng, lat]
            gm.addBuilding(id);
        }

        // Kết nối K điểm gần nhất
        int K = 4;
        for (String u : coords.keySet()) {
            List<Map.Entry<String, Double>> list = new ArrayList<>();
            for (String v : coords.keySet()) {
                if (u.equals(v)) continue;
                double dx = coords.get(u)[0] - coords.get(v)[0];
                double dy = coords.get(u)[1] - coords.get(v)[1];
                double d = Math.hypot(dx, dy) * 111000;
                list.add(Map.entry(v, d));
            }
            list.sort(Comparator.comparingDouble(Map.Entry::getValue));
            for (int i = 0; i < Math.min(K, list.size()); i++) {
                String v = list.get(i).getKey();
                double d = list.get(i).getValue();
                double t = d / 1.4;
                gm.addPath(u, v, d, t, false);
            }
        }
    }

    // Đọc tuyến tùy chỉnh từ campus_edges.csv
    static void loadFixedEdges(String filename, GraphManager gm) throws IOException {
        if (!Files.exists(Paths.get(filename))) return;

        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (String line : lines.subList(1, lines.size())) {
            var cols = line.split(",");
            if (cols.length < 3) continue;
            String from = cols[0].trim();
            String to = cols[1].trim();
            double d = Double.parseDouble(cols[2].trim());
            double t = d / 1.4;
            gm.addPath(from, to, d, t, false);
        }
    }
}
