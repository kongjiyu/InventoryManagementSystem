package DataAccessObject;

import DatabaseTools.StockRequestTools;
import Entity.StockRequest;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StockRequestDAO {
    public static void requestStock() {
        Scanner scanner = new Scanner(System.in);
        StockRequestTools stockRequestTools = new StockRequestTools();
        String continueEntering;

        do {
            System.out.println("Enter Stock Request Details");

            String requestId = stockRequestTools.generateRequestId();

            System.out.print("Enter Item ID: ");
            String itemId = scanner.nextLine();

            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Requestor Name: ");
            String requestedBy = scanner.nextLine();

            System.out.print("Enter Warehouse Location: ");
            String warehouseLocation = scanner.nextLine();

            LocalDate requestDate = LocalDate.now();

            StockRequest request = new StockRequest(requestId, itemId, quantity, requestedBy, warehouseLocation, requestDate);

            StockRequestTools.insertStockRequest(request);

            //Ask Staff want to enter another stock or not
            System.out.print("Do you want to enter another stock request? (Y/N): ");
            continueEntering = scanner.nextLine();

        } while (continueEntering.equalsIgnoreCase("Y"));

        System.out.println("Displaying all stock requests made:");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        stockRequestTools.getStockRequestLog().forEach(request -> {
            System.out.println("Request ID: " + request.getRequestId() +
                    ", Item ID: " + request.getItemId() +
                    ", Quantity: " + request.getQuantity() +
                    ", Requested By: " + request.getRequestBy() +
                    ", Warehouse: " + request.getWarehouseLocation() +
                    ", Date: " + request.getRequestDate().format(dateFormatter));
        });


        scanner.close();
    }
}
