package DataAccessObject;

import DatabaseTools.TransferTools;
import Entity.Product;
import Entity.Warehouse;
import Entity.Retailer;
import Model.DistributionSet;
import Model.TransferSet;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TransferDAO{

    // transfer the stock from a warehouse to another warehouse
    public boolean transferStock(String warehouseID) {
        // create object
        List<TransferSet> transferList = new ArrayList<TransferSet>();
        WarehouseDAO wDAO = new WarehouseDAO();
        StorageDAO sDAO = new StorageDAO();
        Scanner sc = new Scanner(System.in);
        TransferTools tTools = new TransferTools();
        Product product;
        int quantity = 0;
        String secondWarehouseID;
        // List to transfer stock

        boolean flag = false;
        boolean exit = false;
        do {
            // reset checker
            flag = false;
            exit = false;
            // get product
            product = sDAO.getProductUPC(warehouseID, transferList);
            // check is null then exit
            if (product == null) {
                exit = true;
            }
            // get quantity
            quantity = 0;
            if (!exit) {
                do {
                    // let user to enter quantity
                    System.out.println("\n\n\n\n\n\n\n\n\n\n");
                    System.out.print("Enter the product quantity to transfer [999 to exit] : ");
                    try {
                        quantity = sc.nextInt();
                        sc.nextLine();
                    }catch (InputMismatchException e){
                        sc.nextLine();
                        System.out.println("Invalid Input!");
                        try {
                            Thread.sleep(500);
                        }catch (InterruptedException e1){
                            e1.printStackTrace();
                        }
                        continue;
                    }
                    // exit if type 999
                    if (quantity == 999) {
                        exit = true;
                    }
                    // check valid quantity
                    else if (quantity <= 0 || quantity > product.getQuantity()) {
                        System.out.println("Invalid Quantity");
                        try{
                            Thread.sleep(2000);
                        }catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        quantity = 0;
                    }
                } while (quantity == 0);
            }
            // get warehouse to transfer
            if (!exit) {
                do {
                    Warehouse secondWarehouse = wDAO.getWarehouseToTransfer();
                    // if warehouse is null, set warehouseID to null
                    if (secondWarehouse == null) {
                        secondWarehouseID = "XXX";
                        exit = true;
                    }else {
                        // get the warehouseID if not null
                        secondWarehouseID = secondWarehouse.getWarehouseId();
                        // validation check
                        if (secondWarehouseID.equals(warehouseID)) {
                            System.out.println("Cannot be the same warehouse!");
                            try{
                                Thread.sleep(2000);
                            }catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } while (secondWarehouseID.equals(warehouseID));
                // add to the transfer list if exit not true
                if (!exit) {
                    transferList.add(new TransferSet(warehouseID, secondWarehouseID, product.getUPC(), quantity));
                }
            }
            // check whether have more transfer or not
            boolean validationCheck = false;
            do {
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.print("More transfer?<Y/N>: ");
                String choice = sc.nextLine();
                if (choice.equalsIgnoreCase("y")) {
                    flag = true;
                    validationCheck = true;
                }else if (choice.equalsIgnoreCase("n")) {
                    flag = false;
                    validationCheck = true;
                }else {
                    System.out.println("Invalid input!");
                    try{
                        Thread.sleep(2000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }while(!validationCheck);
        }while(flag);
        // start transfer
        if(!transferList.isEmpty()) {
            // List out all the transfer sets in table format
            System.out.println("\nYou are about to transfer the following items:\n");
            // Print table header
            System.out.println("----------------------------------------------------------------------");

            System.out.printf("| %-35s | %-15s | %-10s |\n", "First Warehouse -> Second Warehouse", "ProductUPC", "Quantity");
            System.out.println("----------------------------------------------------------------------");
            // Iterate over the transfer list and print each transfer set
            for (TransferSet transfer : transferList) {
                String warehouses = transfer.getFirstWarehouseID() + " -> " + transfer.getSecondWarehouseID();
                System.out.printf("| %-35s | %-15d | %-10d |\n", warehouses, transfer.getProductUPC(), transfer.getQuantity());
            }
            System.out.println("----------------------------------------------------------------------");
            // Confirm with user
            boolean validInput = false;
            String userChoice = "";
            do {
                System.out.print("\nDo you want to proceed with the transfer? (Y/N): ");
                userChoice = sc.nextLine();
                if (userChoice.equalsIgnoreCase("Y")) {
                    validInput = true;
                    // proceed with transfer
                    for (TransferSet transfer : transferList) {
                        tTools.transferStock(transfer.getFirstWarehouseID(), transfer.getSecondWarehouseID(), transfer.getProductUPC(), transfer.getQuantity());
                        tTools.recordTransfer(tTools.getNewTransferID(), transfer.getFirstWarehouseID(), transfer.getSecondWarehouseID(), transfer.getProductUPC(), transfer.getQuantity());
                    }
                    return true;
                } else if (userChoice.equalsIgnoreCase("N")) {
                    validInput = true;
                    // do not proceed
                    System.out.println("Transfer canceled.");
                    return false;
                } else {
                    System.out.println("Invalid input! Please enter Y or N.");
                }
            } while (!validInput);
        }else{
            return false;
        }
        return false;
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
                        tTools.distributeStock(warehouseID, distribution.getRetailerID(), distribution.getProductUPC(), distribution.getQuantity());

                        // Get the price of the product
                        double price = product.getPrice(); // Assuming price per unit

                        // Get the staff ID who processed the distribution
                        String receivedBy = null;

                        // Record the distribution in the Inventory table
                        tTools.recordDistribution(
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

