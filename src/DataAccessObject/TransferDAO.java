package DataAccessObject;

import DatabaseTools.TransferTools;
import Entity.Product;
import Entity.Warehouse;

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
                            Thread.sleep(1000);
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
                    else if (quantity < 0 || quantity > product.getQuantity()) {
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
                    Warehouse secondWarehouse = wDAO.getWarehouse();
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
            for (TransferSet transfer : transferList) {
                tTools.transferStock(transfer.getFirstWarehouseID(), transfer.getSecondWarehouseID(), transfer.getProductUPC(), transfer.getQuantity());
                tTools.recordTransfer(tTools.getNewTransferID(), transfer.getFirstWarehouseID(), transfer.getSecondWarehouseID(), transfer.getProductUPC(), transfer.getQuantity());
            }
            return true;
        }else{
            return false;
        }
    }
}

