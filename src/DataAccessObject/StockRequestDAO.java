package DataAccessObject;

import DatabaseTools.StaffTools;
import DatabaseTools.StockRequestTools;
import Entity.StockRequest;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StockRequestDAO {

    public static void requestStock() {
        Scanner scanner = new Scanner(System.in);
        String continueEntering;

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
                if (!StockRequestTools.ValidateProductUPC(productUPC)) {
                    System.out.println("Invalid Product UPC. Please enter a valid one.");
                }
            } while (!StockRequestTools.ValidateProductUPC(productUPC));
            request.setProductUPC(productUPC);

            int quantity;
            do {
                System.out.print("Enter Quantity (must be a positive number): ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                }
                quantity = scanner.nextInt();
                scanner.nextLine();
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
                if (!StockRequestTools.ValidateWarehouseId(warehouseId)) {
                    System.out.println("Invalid Warehouse ID. Please enter a valid one.");
                }
            } while (!StockRequestTools.ValidateWarehouseId(warehouseId));
            request.setWarehouseId(warehouseId);

            LocalDate requestDate = LocalDate.now();
            request.setRequestDate(requestDate);

            StockRequestTools.insertStockRequest(request);

            System.out.print("Do you want to enter another stock request? (Y/N): ");
            continueEntering = scanner.nextLine();

        } while (continueEntering.equalsIgnoreCase("Y"));

        System.out.println("Displaying all stock requests made:");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StockRequestTools.getStockRequestLog().forEach(request -> {
            System.out.println("Request ID: " + request.getRequestId() +
                    ", Item ID: " + request.getProductUPC() +
                    ", Quantity: " + request.getQuantity() +
                    ", Requested By: " + request.getRequestBy() +
                    ", Warehouse: " + request.getWarehouseId() +
                    ", Date: " + request.getRequestDate().format(dateFormatter));
        });

        scanner.close();
    }
}
