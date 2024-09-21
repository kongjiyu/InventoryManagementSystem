package DataAccessObject;

import DatabaseTools.LogTools;
import Entity.Log;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class LogDAO {
    public static void displayLogList() {
        List<Log> logList = LogTools.getLogList();

        if (logList == null || logList.isEmpty()) {
            System.out.println("No logs found!");
        } else {
            Scanner scanner = new Scanner(System.in);
            int totalLogs = logList.size();
            final int logsPerPage = 5;  // Number of logs to show per page
            int page = 0;
            int maxPages = (totalLogs - 1) / logsPerPage;
            boolean exit = false;

            do {
                int startIndex = page * logsPerPage;
                int endIndex = Math.min(startIndex + logsPerPage, totalLogs);

                // Display the current page of logs in a table format
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("Log List:");
                System.out.println("=============================================");
                System.out.printf("|%-25s | %-15s|\n", "Time", "Staff ID");
                System.out.println("=============================================");

                for (int i = startIndex; i < endIndex; i++) {
                    Log log = logList.get(i);
                    System.out.printf("|%-25s | %-15s|\n", log.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), log.getStaffID());
                }

                System.out.println("=============================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total logs: %d\n", totalLogs);
                System.out.println("[\"A\" for previous page]\t\t[\"Q\" to exit]\t\t[\"D\" for next page]");
                System.out.print("Select navigation option: ");

                String input = scanner.nextLine().trim();

                if (input.length() == 1) {
                    char option = input.charAt(0);

                    switch (option) {
                        case 'A':
                        case 'a':
                            if (page > 0) page--;
                            break;
                        case 'D':
                        case 'd':
                            if (page < maxPages) page++;
                            break;
                        case 'Q':
                        case 'q':
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid input. Please enter 'A', 'D', or 'Q'.");
                            try {
                                Thread.sleep(500);
                            }catch (InterruptedException ie) {
                                ie.printStackTrace();
                            }
                    }
                } else {
                    System.out.println("Invalid input. Please enter a single character.");
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }

            } while (!exit);
        }
    }

}
