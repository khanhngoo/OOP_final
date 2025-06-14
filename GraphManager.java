// package campuspathfinder;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple GraphManager matching the PathfindingEngine API:
 * - adjacency list of weighted, flagged edges
 * - CRUD operations on nodes (buildings) and edges (paths)
 */
public class GraphManager {
    /**
     * Represents an edge to a neighbor with distance, time cost, and a fixed-flag.
     */
    public static class Edge {
        public final String to;
        public final double distance;
        public final double time;
        public final boolean fixedFlag;

        public Edge(String to, double distance, double time, boolean fixedFlag) {
            this.to = to;
            this.distance = distance;
            this.time = time;
            this.fixedFlag = fixedFlag;
        }
    }

    // adjacency-list: node ID -> (neighbor ID -> Edge)
    private final Map<String, Map<String, Edge>> adj = new HashMap<>();

    /** Add a node if not already present. */
    public void addBuilding(String id) {
        adj.putIfAbsent(id, new HashMap<>());
    }

    /** Remove a node and all incident edges. */
    public void removeBuilding(String id) {
        adj.remove(id);
        for (Map<String, Edge> nbrs : adj.values()) {
            nbrs.remove(id);
        }
    }

    /**
     * Add an undirected edge (path) between two nodes, creating nodes if needed.
     */
    public void addPath(String from, String to, double distance, double time, boolean fixedFlag) {
        addBuilding(from);
        addBuilding(to);
        adj.get(from).put(to, new Edge(to, distance, time, fixedFlag));
        adj.get(to).put(from, new Edge(from, distance, time, fixedFlag));
    }

    /** Remove the undirected edge between two nodes. */
    public void removePath(String from, String to) {
        if (adj.containsKey(from)) adj.get(from).remove(to);
        if (adj.containsKey(to))   adj.get(to).remove(from);
    }

    /**
     * Get the adjacency list for use in pathfinding.
     * @return a map nodeID -> (neighborID -> Edge)
     */
    public Map<String, Map<String, Edge>> getAdjacencyList() {
        return adj;
    }
}

