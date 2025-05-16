package usecase;

import model.Building;
import model.CampusMap;
import model.Path;

public class adminUseCase {
    public static void addBuildingAdmin(CampusMap campusMap, Building building) {
        campusMap.buildings.put(building.getId(), building);
    }

    public static void removeBuildingAdmin(CampusMap campusMap, Building building) {

    }

    public static void addPathAdmin(CampusMap campusMap, Path path) {

    }

    public static void removePathAdmin(CampusMap campusMap, Path path) {

    }
}
