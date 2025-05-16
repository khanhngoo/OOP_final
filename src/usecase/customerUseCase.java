package usecase;

import model.CampusMap;
import model.Path;

import java.util.Scanner;

public class customerUseCase {
    public static void useCase1(CampusMap campusMap) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter start building ID: ");
        String start = scanner.nextLine().trim();
        System.out.print("Enter destination building ID: ");
        String dest = scanner.nextLine().trim();

        if (!campusMap.getBuildingIds().contains(start) || !campusMap.getBuildingIds().contains(dest)) {
            System.out.println("Unknown building ID(s). Please check and try again.");
        } else {
            boolean found = false;
            for (Path p : campusMap.getNeighbors(start)) {
                if (p.getToId().equals(dest)) {
                    System.out.printf("Distance from %s to %s: %.1f m\n", start, dest, p.getDistanceMeters());
                    System.out.printf("Estimated time from %s to %s: %.1f s\n", start, dest, p.getTimeSeconds());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("No direct path found from " + start + " to " + dest + ".");
            }
        }
        scanner.close();
    }
}
