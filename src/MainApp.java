import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import model.*;
import usecase.*;
import utils.*;

// Model classes

public class MainApp {

    public static void main(String[] args) {

        System.out.println("Welcome to CamNAVI, VinUniversity pioneer in guiding new students around the campus, calculating the best route and time for you to always when to class on time");
        System.out.print("Are you a Student or Developer (S/D)");
        Scanner scanner = new Scanner(System.in);
        String role = scanner.nextLine().trim();

        // load the csv in boi
        String buildingCsvPath = pathTeam.khanh_building_csv;
        String pathsCsvPath    = pathTeam.khanh_path_csv;

        CampusMap campusMap = new CampusMap();
        try {
            loadCSV.loadBuildings(buildingCsvPath, campusMap);
            loadCSV.loadPaths(pathsCsvPath, campusMap);
        } catch (IOException e) {
            System.err.println("Error loading CSV files: " + e.getMessage());
            return; // or handle error accordingly
        }

        if (role.equals("S")) {
            customerUseCase.useCase1(campusMap);
        }
        else if (role.equals("D")) {

        }
    }
}
    