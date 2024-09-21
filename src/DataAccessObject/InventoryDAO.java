package DataAccessObject;

import DatabaseTools.InventoryTools;
import DatabaseTools.StorageTools;
import DatabaseTools.SupplierTools;
import Driver.Utils;
import Entity.Inventory;

import java.util.Scanner;

public class InventoryDAO {
    Scanner scanner = new Scanner(System.in);

    public void stockIn(String warehouseID, String staffID) {
        Inventory inventory = new Inventory();
        InventoryTools inventoryTools = new InventoryTools();
        boolean isConfirmed = false;
        StorageTools storageTools = new StorageTools();
        System.out.println("Stock In");

        // Collect initial details
        inventory.setProductUPC(Utils.getIntInput("Please enter product UPC: "));
        inventory.setQuantity(Utils.getIntInput("Please enter quantity: "));

        // Validate supplier ID input
        do {
            System.out.print("Please enter supplier ID: ");
            inventory.setSupplierID(scanner.nextLine());
            if (SupplierTools.retrieveSupplier(inventory.getSupplierID()) == null) {
                System.out.println("Invalid Supplier ID! Please try again.");
            }
        } while (SupplierTools.retrieveSupplier(inventory.getSupplierID()) == null);

        inventory.setPrice(Utils.getDoubleInput("Please enter total price: "));
        System.out.print("Please enter remarks (Press Enter if no remarks): ");
        inventory.setRemarks(scanner.nextLine());

        // Confirmation and option to reset fields
        while (!isConfirmed) {
            // Display entered details for confirmation
            System.out.println("==================================================================================");
            System.out.printf("%-11s|%-10s|%11s|%-15s|%-100s\n", "Product UPC", "Quantity", "Supplier ID", "Total Price", "Remarks");
            System.out.println("==================================================================================");
            System.out.printf("%-11s|%-10d|%-11s|RM%-13.2f|%-100s\n", inventory.getProductUPC(), inventory.getQuantity(), inventory.getSupplierID(), inventory.getPrice(), inventory.getRemarks());
            System.out.println("==================================================================================");
            System.out.println("The stock in details are correct? (Y to confirm, N to modify fields): ");
            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("Y")) {
                // Set remaining inventory attributes and insert data
                inventory.setId(inventoryTools.getNewInventoryID());
                inventory.setInventoryType("Stock In");
                inventory.setReceivedBy(staffID);
                inventory.setWarehouseID(warehouseID);
                inventoryTools.insertStockIn(inventory);
                storageTools.insertProductIntoStorage(inventory.getWarehouseID(), inventory.getProductUPC(), inventory.getQuantity(), "Warehouse");
                System.out.println("Stock-in entry successfully recorded.");
                isConfirmed = true;  // Exit the loop
            } else if (option.equalsIgnoreCase("N")) {
                // Allow user to modify specific fields
                modifyInventoryFields(inventory);
            } else {
                System.out.println("Invalid input! Please enter 'Y' to confirm or 'N' to modify.");
            }
        }
    }

    // Function to modify specific fields
    private void modifyInventoryFields(Inventory inventory) {
        System.out.println("Which field would you like to modify?");
        System.out.println("1. Product UPC");
        System.out.println("2. Quantity");
        System.out.println("3. Supplier ID");
        System.out.println("4. Total Price");
        System.out.println("5. Remarks");
        int choice = Utils.getIntInput("Enter the number of the field to modify: ");

        switch (choice) {
            case 1:
                inventory.setProductUPC(Utils.getIntInput("Please enter new product UPC: "));
                break;
            case 2:
                inventory.setQuantity(Utils.getIntInput("Please enter new quantity: "));
                break;
            case 3:
                do {
                    System.out.print("Please enter new supplier ID: ");
                    inventory.setSupplierID(scanner.nextLine());
                    if (SupplierTools.retrieveSupplier(inventory.getSupplierID()) == null) {
                        System.out.println("Invalid Supplier ID! Please try again.");
                    }
                } while (SupplierTools.retrieveSupplier(inventory.getSupplierID()) == null);
                break;
            case 4:
                inventory.setPrice(Utils.getDoubleInput("Please enter new total price: "));
                break;
            case 5:
                System.out.print("Please enter new remarks: ");
                inventory.setRemarks(scanner.nextLine());
                break;
            default:
                System.out.println("Invalid choice. No fields were modified.");
                break;
        }
    }

}
