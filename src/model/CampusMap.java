package model;

import java.util.*;

// CampusMap holds the graph
public class CampusMap {
    public Map<String, Building> buildings = new HashMap<>();
    public Map<String, List<Path>> adjacency = new HashMap<>();

    public void addBuilding(Building b) {
        buildings.put(b.getId(), b);
        adjacency.putIfAbsent(b.getId(), new ArrayList<>());
    }

    // not sure if needed
    public Set<String> getBuildingIds() {
        return buildings.keySet();
    }

    public void addPath(Path p) {
        adjacency.get(p.getFromId()).add(p);
        // bidirectional
        Path reverse = new Path(p.getToId(), p.getFromId(), p.getDistanceMeters(), p.getTimeSeconds());
        adjacency.get(reverse.getFromId()).add(reverse);
    }

    public Building getBuilding(String id) {
        return buildings.get(id);
    }

    public List<Path> getNeighbors(String id) {
        return adjacency.getOrDefault(id, Collections.emptyList());
    }
}
