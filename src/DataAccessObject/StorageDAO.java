package DataAccessObject;

import java.util.*;

import DatabaseTools.ProductTools;
import DatabaseTools.StorageTools;
import Entity.Product;
import Model.TransferSet;

import static Driver.Utils.getIntInput;

public class StorageDAO {

    //let user choose Product based on branch
    public Product getProductUPC(String warehouseID, List<TransferSet> transferList) {

        //create object of product
        Product product = null;

        //create scanner object
        Scanner scanner = new Scanner(System.in);

        //create object for storageTools
        StorageTools st = new StorageTools();

        //create object for product
        ProductTools pt = new ProductTools();

        int option=0;
        // let user choose which style to search ProductUPC
        do{

            // get user prefer style to search product
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("[1] Search By Enter Product Name");
            System.out.println("[2] Search By Enter Product SKU");
            System.out.println("[3] Search By Check Product List");
            System.out.println("[4] Exit");
            System.out.print(" > ");
            try {
                option = scanner.nextInt();
                scanner.nextLine();
            }catch(InputMismatchException e){
                System.out.println("Invalid input!");
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e2){
                    e2.printStackTrace();
                }
                scanner.nextLine();
                continue;
            }
            switch(option) {
                case 1:
                    // search by product name
                    System.out.print("Enter Product Name: ");
                    String productName = scanner.nextLine();

                    product = st.getProductByNameANDWarehouseID(productName, warehouseID);
                    break;
                case 2:
                    //search by product SKU
                    int productUPC = getIntInput("Enter Product UPC: ");
                    // set product when get the product
                    product = st.getProductByUPCANDWarehouseID(productUPC, warehouseID);
                    break;
                case 3:
                    product = getProductByList(warehouseID, transferList);
                    break;
                case 4:
                    return null;
                default:
                    System.out.println("Invalid Input!");
                    try{
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            // Confirmation for the chosen product
            if (product != null) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("Product Detail Confirmation");
                System.out.println("=====================================================================================================");
                System.out.printf("|%-15s|%-20s|%-20s|%-20s|%-20s|\n", "Product UPC", "Product Name", "Product Category", "Product Price", "Product Quantity");
                System.out.println("=====================================================================================================");
                System.out.printf("|%-15s|%-20s|%-20s|RM%-18.2f|%-20d|\n", product.getUPC(), product.getName(), product.getCategory(), product.getPrice(), product.getQuantity());
                System.out.println("=====================================================================================================");

                String confirmation;
                do {
                    System.out.print("Confirm this product? <Y/N> : ");
                    confirmation = scanner.nextLine().trim().toUpperCase();

                    // If input is "N", clear the product and return to loop
                    if (confirmation.equals("N")) {
                        product = null;
                        break;
                    } else if (!confirmation.equals("Y")) {
                        // If input is neither "Y" nor "N", show invalid input message
                        System.out.println("Invalid input! Please enter Y or N.");
                    }
                } while (!confirmation.equals("Y") && !confirmation.equals("N"));
            }
        }while(product == null);
        return product;
    }

    public Product getProductByList(String warehouseID, List<TransferSet> transferList) {
        //create scanner object
        Scanner scanner = new Scanner(System.in);
        //StorageTools object
        StorageTools st = new StorageTools();
        //product that going to return
        Product product = null;
        // create product ArrayList for listing
        List<Product> productList = new ArrayList<>();
        // get the product list
        productList = st.getProductListByWarehouseIDAndException(warehouseID, transferList);
        // check there is product or not
        if(productList.isEmpty()) {
            System.out.println("No Product Available");
            try{
                Thread.sleep(500);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            return product;
        }
        //print by page
        int totalIndex = productList.size();
        // how many item in 1 page
        final int product_per_page = 5;
        int page = 0;
        // get the maximum pages can print
        int max_pages = (totalIndex-1) / product_per_page;
        // check whether the prodcut been choosen or not
        boolean check = false;
        do {
            int count = 1;
            // get the start index and end index
            int startIndex = page * product_per_page;
            int endIndex = startIndex + product_per_page < totalIndex ? startIndex + product_per_page : totalIndex;
            // print the product within the index
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("=========================================================================================================");
            System.out.printf("|%-3s|%-15s|%-20s|%-20s|%-20s|%-20s|\n", "No.", "Product UPC", "Product Name", "Product Category", "Product Price", "Product Quantity");
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println("=========================================================================================================");
                System.out.printf("|%-3d|%-15s|%-20s|%-20s|RM%-18.2f|%-20d|\n",
                        count,
                        productList.get(i).getUPC(),
                        productList.get(i).getName(),
                        productList.get(i).getCategory(),
                        productList.get(i).getPrice(),
                        productList.get(i).getQuantity());
                count++;
            }
            System.out.println("=========================================================================================================");
            System.out.printf("Page %d of %d\n", page+1, max_pages + 1);
            System.out.printf("Total product: %d\n", totalIndex);
            System.out.println("[\"A\" for last page]\t\t\t\t\t\t[\"Q\" to exit]\t\t\t\t\t\t[\"D\" for next page]");
            System.out.print("Input: ");
            char input = scanner.nextLine().charAt(0);
            switch (input) {
                case 'A':
                case 'a':
                    if (page > 0)
                        page--;
                    break;
                case 'D':
                case 'd':
                    if (page < max_pages)
                        page++;
                    break;
                case 'Q':
                case 'q':
                    check = true;
                    break;
                default:
                    if (Character.isDigit(input)){
                        int num = input - '0';
                        if (num<=5 && num>0){
                            try {
                                product = productList.get(startIndex + num - 1);
                                check = true;
                            }catch (IndexOutOfBoundsException ie) {
                                continue;
                            }
                        }
                    }
                    break;
            }
        }while(!check);
        return product;
    }

    public void displayByWarehouse(String warehouseID) {
        StorageTools st = new StorageTools();
        Scanner scanner = new Scanner(System.in);
        List<Product> productList = st.getProductListByWarehouseID(warehouseID);

        if(productList.isEmpty()) {
            System.out.println("No Product Available");
            try{
                Thread.sleep(500);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        //print by page
        int totalIndex = productList.size();
        // how many item in 1 page
        final int product_per_page = 5;
        int page = 0;
        // get the maximum pages can print
        int max_pages = (totalIndex-1) / product_per_page;
        // check whether the prodcut been choosen or not
        boolean check = false;
        do {
            int count = 1;
            // get the start index and end index
            int startIndex = page * product_per_page;
            int endIndex = startIndex + product_per_page < totalIndex ? startIndex + product_per_page : totalIndex;
            // print the product within the index
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("=========================================================================================================");
            System.out.printf("|%-3s|%-15s|%-20s|%-20s|%-20s|%-20s|\n", "No.", "Product UPC", "Product Name", "Product Category", "Product Price", "Product Quantity");
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println("=========================================================================================================");
                System.out.printf("|%-3d|%-15s|%-20s|%-20s|RM%-18.2f|%-20d|\n",
                        count,
                        productList.get(i).getUPC(),
                        productList.get(i).getName(),
                        productList.get(i).getCategory(),
                        productList.get(i).getPrice(),
                        productList.get(i).getQuantity());
                count++;
            }
            System.out.println("=========================================================================================================");
            System.out.printf("Page %d of %d\n", page+1, max_pages + 1);
            System.out.printf("Total product: %d\n", totalIndex);
            System.out.println("[\"A\" for last page]\t\t\t\t\t\t[\"Q\" to exit]\t\t\t\t\t\t[\"D\" for next page]");
            System.out.print("Input: ");
            char input = scanner.nextLine().charAt(0);
            switch (input) {
                case 'A':
                case 'a':
                    if (page > 0)
                        page--;
                    break;
                case 'D':
                case 'd':
                    if (page < max_pages)
                        page++;
                    break;
                case 'Q':
                case 'q':
                    check = true;
                    break;
                default:
                    System.out.println("Invalid options!");
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }while(!check);
    }
}
