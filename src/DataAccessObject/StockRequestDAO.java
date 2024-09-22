package DataAccessObject;

import DatabaseTools.StaffTools;
import DatabaseTools.StockRequestTools;
import Driver.Utils;
import Entity.StockRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StockRequestDAO {

    public static void requestStock() {
        List<StockRequest> stockRequestLog = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String continueEntering;
        boolean check = false;
        do {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Enter Stock Request Details");

            StockRequest request = new StockRequest();

            String requestId = StockRequestTools.retrieveMaxRequestID();
            request.setRequestId(requestId);

            String productUPC;
            do {
                System.out.print("Enter Product UPC: ");
                productUPC = scanner.nextLine();
                check = StockRequestTools.ValidateProductUPC(productUPC);
                if (!check) {
                    System.out.println("Invalid Product UPC. Please enter a valid one.");
                }
            } while (!check);
            request.setProductUPC(productUPC);
            check = false;

            int quantity;
            do {
                quantity = Utils.getIntInput("Enter Quantity (must be a positive number): ");
            } while (quantity <= 0);
            request.setQuantity(quantity);


            do {
                System.out.print("Enter Requester ID: ");
                request.setRequestBy(scanner.nextLine());
                if (!StaffTools.checkID(request.getRequestBy())){
                    System.out.println("Invalid Staff ID!");
                    request.setRequestBy(null);
                }
            }while (request.getRequestBy() == null);

            String warehouseId;
            do {
                System.out.print("Enter Warehouse ID: ");
                warehouseId = scanner.nextLine();
                check = StockRequestTools.ValidateWarehouseId(warehouseId);
                if (!check) {
                    System.out.println("Invalid Warehouse ID. Please enter a valid one.");
                }
            } while (!check);
            request.setWarehouseId(warehouseId);

            LocalDate requestDate = LocalDate.now();
            request.setRequestDate(requestDate);
            stockRequestLog.add(request);
            StockRequestTools.insertStockRequest(request);
            do {
                System.out.print("Do you want to enter another stock request? (Y/N): ");
                continueEntering = scanner.nextLine();
                check = continueEntering.equalsIgnoreCase("Y") || continueEntering.equalsIgnoreCase("N");
                if (!check) {
                    System.out.println("Invalid Input!");
                }
            } while(!check);
        } while (continueEntering.equalsIgnoreCase("Y"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (stockRequestLog.isEmpty()) {
            System.out.println("No stock requests found!");
        } else {
            int totalRequests = stockRequestLog.size();
            final int requestsPerPage = 5;  // Number of requests to show per page
            int page = 0;
            int maxPages = (totalRequests - 1) / requestsPerPage;
            boolean exit = false;

            do {
                int startIndex = page * requestsPerPage;
                int endIndex = Math.min(startIndex + requestsPerPage, totalRequests);

                // Display the current page of stock request logs
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("==============================================================================================");
                System.out.printf("|%-15s | %-10s | %-10s | %-15s | %-15s | %-12s|\n", "Request ID", "Item ID", "Quantity", "Requested By", "Warehouse", "Request Date");
                System.out.println("==============================================================================================");

                for (int i = startIndex; i < endIndex; i++) {
                    StockRequest request = stockRequestLog.get(i);
                    System.out.printf("|%-15s | %-10s | %-10d | %-15s | %-15s | %-12s|\n",
                            request.getRequestId(),
                            request.getProductUPC(),
                            request.getQuantity(),
                            request.getRequestBy(),
                            request.getWarehouseId(),
                            request.getRequestDate().format(dateFormatter));
                }

                System.out.println("==============================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total requests: %d\n", totalRequests);
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

    public static void displayStockRequests() {
        List<StockRequest> stockRequests = StockRequestTools.retrieveAllStockRequests();

        if (stockRequests == null || stockRequests.isEmpty()) {
            System.out.println("No stock requests found!");
        } else {
            Scanner scanner = new Scanner(System.in);
            int totalRequests = stockRequests.size();
            final int requestsPerPage = 5;  // Number of stock requests to show per page
            int page = 0;
            int maxPages = (totalRequests - 1) / requestsPerPage;
            boolean exit = false;

            do {
                int startIndex = page * requestsPerPage;
                int endIndex = Math.min(startIndex + requestsPerPage, totalRequests);

                // Display the current page of stock requests in a table format
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("Stock Request List:");
                System.out.println("=======================================================================================");
                System.out.printf("|%-10s | %-11s | %-10s | %-15s | %-12s | %-12s|\n", "Request ID", "Product UPC", "Quantity", "Requested By", "Warehouse ID", "Request Date");
                System.out.println("=======================================================================================");

                for (int i = startIndex; i < endIndex; i++) {
                    StockRequest request = stockRequests.get(i);
                    System.out.printf("|%-10s | %-11s | %-10d | %-15s | %-12s | %-12s|\n",
                            request.getRequestId(),
                            request.getProductUPC(),
                            request.getQuantity(),
                            request.getRequestBy(),
                            request.getWarehouseId(),
                            request.getRequestDate().toString());
                }

                System.out.println("=======================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total stock requests: %d\n", totalRequests);
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
