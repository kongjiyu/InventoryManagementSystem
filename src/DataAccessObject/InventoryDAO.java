package DataAccessObject;

import DatabaseTools.InventoryTools;
import DatabaseTools.StorageTools;
import DatabaseTools.SupplierTools;
import DatabaseTools.TransferTools;
import Driver.Utils;
import Entity.Inventory;
import Entity.Product;
import Entity.Retailer;
import Model.DistributionSet;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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

    // Distribute stock from a warehouse to multiple retailers
    public boolean distributeStock(String warehouseID) {
        // Create objects
        List<DistributionSet> distributionList = new ArrayList<>();
        WarehouseDAO wDAO = new WarehouseDAO();
        StorageDAO sDAO = new StorageDAO();
        RetailerDAO rDAO = new RetailerDAO();
        Scanner sc = new Scanner(System.in);
        TransferTools tTools = new TransferTools();
        InventoryTools iTools = new InventoryTools();
        Product product;
        int totalQuantity = 0;
        List<Retailer> selectedRetailers = new ArrayList<>();

        boolean exit = false;

        // Step 1: Select product from warehouse's stock
        product = sDAO.getProductUPC(warehouseID, new ArrayList<>());
        if (product == null) {
            System.out.println("No product selected.");
            return false;
        }

        // Step 2: Enter total quantity to distribute
        do {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.print("Enter the total quantity to distribute [999 to exit]: ");
            try {
                totalQuantity = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid Input!");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                continue;
            }
            if (totalQuantity == 999) {
                return false;
            } else if (totalQuantity <= 0 || totalQuantity > product.getQuantity()) {
                System.out.println("Invalid Quantity");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                totalQuantity = 0;
            }
        } while (totalQuantity == 0);

        // Step 3: Select multiple retailers
        boolean addMoreRetailers = false;
        do {
            Retailer retailer = rDAO.getRetailer();
            if (retailer == null) {
                System.out.println("No retailer selected.");
                if (selectedRetailers.isEmpty()) {
                    return false;
                } else {
                    break;
                }
            } else {
                // Check if retailer is already selected
                boolean alreadySelected = false;
                for (Retailer r : selectedRetailers) {
                    if (r.getRetailerId().equals(retailer.getRetailerId())) {
                        alreadySelected = true;
                        break;
                    }
                }
                if (alreadySelected) {
                    System.out.println("Retailer already selected.");
                } else {
                    // Add to selected retailers list
                    selectedRetailers.add(retailer);
                }
            }

            // Ask if user wants to add more retailers
            boolean validInput = false;
            do {
                System.out.print("Do you want to add more retailers? (Y/N): ");
                String choice = sc.nextLine();
                if (choice.equalsIgnoreCase("Y")) {
                    addMoreRetailers = true;
                    validInput = true;
                } else if (choice.equalsIgnoreCase("N")) {
                    addMoreRetailers = false;
                    validInput = true;
                } else {
                    System.out.println("Invalid input! Please enter Y or N.");
                }
            } while (!validInput);
        } while (addMoreRetailers);

        if (selectedRetailers.isEmpty()) {
            System.out.println("No retailers selected for distribution.");
            return false;
        }

        // Step 4: Calculate quantity per retailer
        int numRetailers = selectedRetailers.size();
        int quantityPerRetailer = totalQuantity / numRetailers;
        int remainder = totalQuantity % numRetailers;

        // Step 5: Create distribution list
        for (Retailer retailer : selectedRetailers) {
            int quantity = quantityPerRetailer;
            if (remainder > 0) {
                quantity += 1;
                remainder--;
            }
            distributionList.add(new DistributionSet(warehouseID, retailer.getRetailerId(), product.getUPC(), quantity));
        }

        // Step 6: Confirm distributions with user
        if (!distributionList.isEmpty()) {
            // List out all the distribution sets in table format
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("\nYou are about to distribute the following items:\n");
            // Print table header
            System.out.println("=======================================================");
            System.out.printf("| %-20s | %-15s | %-10s |\n", "Retailer ID", "Product UPC", "Quantity");
            System.out.println("=======================================================");
            // Iterate over the distribution list and print each distribution set
            for (DistributionSet distribution : distributionList) {
                System.out.printf("| %-20s | %-15d | %-10d |\n", distribution.getRetailerID(), distribution.getProductUPC(), distribution.getQuantity());
            }
            System.out.println("=======================================================");
            // Confirm with user
            boolean validInput = false;
            String userChoice = "";
            do {
                System.out.print("\nDo you want to proceed with the distribution? (Y/N): ");
                userChoice = sc.nextLine();
                if (userChoice.equalsIgnoreCase("Y")) {
                    validInput = true;
                    for (DistributionSet distribution : distributionList) {
                        // Distribute stock to retailers
                        iTools.distributeStock(warehouseID, distribution.getRetailerID(), distribution.getProductUPC(), distribution.getQuantity());

                        // Get the price of the product
                        double price = product.getPrice(); // Assuming price per unit

                        // Get the staff ID who processed the distribution
                        String receivedBy = null;

                        // Record the distribution in the Inventory table
                        iTools.recordDistribution(
                                tTools.getNewInventoryID(),
                                warehouseID,
                                distribution.getRetailerID(),
                                distribution.getProductUPC(),
                                distribution.getQuantity(),
                                price,
                                receivedBy
                        );
                    }

                    System.out.println("Distribution completed successfully.");
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;

                } else if (userChoice.equalsIgnoreCase("N")) {
                    validInput = true;
                    // Do not proceed
                    System.out.println("Distribution canceled.");
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                } else {
                    System.out.println("Invalid input! Please enter Y or N.");
                }
            } while (!validInput);
        } else {
            return false;
        }

        return false;
    }
}
