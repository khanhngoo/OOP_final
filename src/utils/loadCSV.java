package utils;

import model.Building;
import model.CampusMap;
import model.Path;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class loadCSV {
    public static void loadBuildings(String csvFile, CampusMap map) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String id = tokens[0].trim();
                String name = tokens[1].trim();
                double lon = Double.parseDouble(tokens[2].trim());
                double lat = Double.parseDouble(tokens[3].trim());
                map.addBuilding(new Building(id, name, lat, lon));
            }
        }
    }

    public static void loadPaths(String csvFile, CampusMap map) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String from = tokens[0].trim();
                String to   = tokens[1].trim();
                double dist = Double.parseDouble(tokens[2].trim());
                double time = Double.parseDouble(tokens[3].trim());
                map.addPath(new Path(from, to, dist, time));
            }
        }
    }
}
