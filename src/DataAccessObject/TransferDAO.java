package DataAccessObject;

import DatabaseTools.TransferTools;
import Entity.Product;
import Entity.Warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransferDAO{

    // transfer the stock from a warehouse to another warehouse
    public boolean transferStock(String warehouseID) {
        // create object
        WarehouseDAO wDAO = new WarehouseDAO();
        StorageDAO sDAO = new StorageDAO();
        Scanner sc = new Scanner(System.in);
        TransferTools tTools = new TransferTools();
        // List to transfer stock
        List<TransferSet> transferList = new ArrayList<TransferSet>();
        boolean flag = false;
        do {
            String secondWarehouseID = wDAO.getWarehouse().getWarehouseId();
            Product product = sDAO.getProductUPC(warehouseID);
            int quantity = 0;
            do {
                // let user to enter quantity
                System.out.print("Enter the product quantity to transfer [999 to exit] : ");
                quantity = sc.nextInt();
                sc.nextLine();
                if (quantity < 0 || quantity > product.getQuantity()) {
                    System.out.println("Invalid Quantity");
                    quantity = 0;
                }
                if (quantity == 999){
                    return false;
                }
            }while (quantity == 0);
            // add to the transfer list
            transferList.add(new TransferSet(warehouseID, secondWarehouseID, product.getUPC(), quantity));
            // check whether have more transfer or not
            boolean validationCheck = false;
            do {
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
                }
            }while(!validationCheck);
        }while(flag == false);
        // start transfer
        for (TransferSet transfer : transferList) {
            tTools.transferStock(transfer.getFirstWarehouseID(), transfer.getSecondWarehouseID(), transfer.getProductUPC(), transfer.getQuantity());
        }
        return true;
    }
}

class TransferSet{
    private String firstWarehouseID;
    private String secondWarehouseID;
    private String ProductUPC;
    private int Quantity;

    TransferSet(String firstWarehouseID, String secondWarehouseID, String ProductUPC, int Quantity) {
        this.firstWarehouseID = firstWarehouseID;
        this.secondWarehouseID = secondWarehouseID;
        this.ProductUPC = ProductUPC;
        this.Quantity = Quantity;
    }

    public String getFirstWarehouseID() {
        return firstWarehouseID;
    }

    public void setFirstWarehouseID(String firstWarehouseID) {
        this.firstWarehouseID = firstWarehouseID;
    }

    public String getSecondWarehouseID() {
        return secondWarehouseID;
    }

    public void setSecondWarehouseID(String secondWarehouseID) {
        this.secondWarehouseID = secondWarehouseID;
    }

    public String getProductUPC() {
        return ProductUPC;
    }

    public void setProductUPC(String productUPC) {
        ProductUPC = productUPC;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
