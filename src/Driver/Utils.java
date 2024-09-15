package Driver;

import java.util.Scanner;

public class Utils {
    static Scanner scanner = new Scanner(System.in);

    // Helper method to get integer input
    public static int getIntInput(String prompt) {
        int input = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            try {
                input = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return input;
    }

    public static double getDoubleInput(String prompt) {
        double input = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            try {
                input = scanner.nextDouble();
                scanner.nextLine(); // Clear the buffer
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return input;
    }
}
