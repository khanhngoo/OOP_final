package model;

import java.util.*;

public class Building {
    private final String id;
    private final String name;
    private final double latitude;
    private final double longitude;

    public Building(String id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}



