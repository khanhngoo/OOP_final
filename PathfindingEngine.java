// package campuspathfinder;

import java.util.*;

/**
 * PathfindingEngine finds the lowest‐cost route between two nodes in a GraphManager.
 */
public class PathfindingEngine {
    /** Holds the accumulated cost components along a path. */
    public static class Cost {
        public final double distance;    // meterss
        public final double time;        // seconds
        public final boolean usedFixed;  // whether any fixed‐flag edge was used

        public Cost(double distance, double time, boolean usedFixed) {
            this.distance   = distance;
            this.time       = time;
            this.usedFixed  = usedFixed;
        }

        /** 
         * Combines components into a single scalar:
         *   total = distance + (time × 10) + (fixedFlag ? 50 : 0)
         */
        public double totalCost() {
            double penalty = usedFixed ? 50.0 : 0.0;
            return distance + time * 10.0 + penalty;
        }
    }

    /** Represents the result: the node sequence plus its final Cost. */
    public static class Result {
        public final List<String> route;
        public final Cost cost;

        public Result(List<String> route, Cost cost) {
            this.route = route;
            this.cost  = cost;
        }
    }

    
    public static Result dijkstra(String src, String dst, GraphManager graph) {
        var adj = graph.getAdjacencyList();
        // best-known cost to each node
        Map<String, Cost> best = new HashMap<>();
        // to reconstruct path
        Map<String, String> prev = new HashMap<>();

        // min-heap ordering by totalCost
        PriorityQueue<String> pq = new PriorityQueue<>(
            Comparator.comparingDouble(node -> best.get(node).totalCost())
        );

        // Initialize
        best.put(src, new Cost(0, 0, false));
        pq.add(src);

        while (!pq.isEmpty()) {
            String u = pq.poll();
            Cost cu = best.get(u);
            if (u.equals(dst)) break;  // early exit

            // Relax all outgoing edges
            for (GraphManager.Edge e : adj.getOrDefault(u, Map.of()).values()) {
                boolean usedFixed = cu.usedFixed || e.fixedFlag;
                Cost candidate = new Cost(
                    cu.distance + e.distance,
                    cu.time     + e.time,
                    usedFixed
                );

                Cost known = best.get(e.to);
                if (known == null || candidate.totalCost() < known.totalCost()) {
                    best.put(e.to, candidate);
                    prev.put(e.to, u);
                    // refresh in priority queue
                    pq.remove(e.to);
                    pq.add(e.to);
                }
            }
        }

        // No route found
        if (!best.containsKey(dst)) return null;

        // Reconstruct the path backward
        List<String> path = new ArrayList<>();
        for (String at = dst; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return new Result(path, best.get(dst));
    }
}
