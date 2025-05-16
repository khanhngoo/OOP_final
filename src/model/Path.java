package model;

public class Path {
    private final String fromId;
    private final String toId;
    private final double distanceMeters;
    private final double timeSeconds;

    public Path(String fromId, String toId, double distanceMeters, double timeSeconds) {
        this.fromId = fromId;
        this.toId = toId;
        this.distanceMeters = distanceMeters;
        this.timeSeconds = timeSeconds;
    }

    public String getFromId() { return fromId; }
    public String getToId() { return toId; }
    public double getDistanceMeters() { return distanceMeters; }
    public double getTimeSeconds() { return timeSeconds; }
}