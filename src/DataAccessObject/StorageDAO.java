package DataAccessObject;

import java.util.*;

import DatabaseTools.ProductTools;
import DatabaseTools.StorageTools;
import Entity.Product;

import static DatabaseTools.ProductTools.retrieveProduct;

public class StorageDAO {

    //let user choose Product based on branch
    public Product getProductSKU(String warehouseID) {

        //create object of product
        Product product = null;

        //create scanner object
        Scanner scanner = new Scanner(System.in);

        //create object for storageTools
        StorageTools st = new StorageTools();

        //create object for product
        ProductTools pt = new ProductTools();

        // let user choose which style to search ProductSKU
        do{
            // get user prefer style to search product
            System.out.println("[1] Search By Enter Product Name");
            System.out.println("[2] Search By Enter Product SKU");
            System.out.println("[3] Search By Check Product List");
            System.out.println("[4] Exit");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch(option) {
                case 1:
                    // search by product name
                    System.out.print("Enter Product Name: ");
                    String productName = scanner.nextLine();

                    product = st.getProductByNameANDWarehouseID(productName, warehouseID);

                case 2:
                    //search by product SKU
                    System.out.print("Enter Product SKU: ");
                    String productSKU = scanner.nextLine();
                    // set product when get the product
                    product = st.getProductBySKUANDWarehouseID(productSKU, warehouseID);

                case 3:
                    product = getProductByList(warehouseID);

                case 4:
                    return null;
            }

        }while(product == null);
        return product;
    }

    public Product getProductByList(String warehouseID) {
        //create scanner object
        Scanner scanner = new Scanner(System.in);
        //StorageTools object
        StorageTools st = new StorageTools();
        //product that going to return
        Product product = null;
        // create product ArrayList for listing
        List<Product> productList = new ArrayList<>();
        // get the product list
        productList = st.getProductListByWarehouseID(warehouseID);
        //print by page
        int totalIndex = productList.size();
        // how many item in 1 page
        final int product_per_page = 5;
        int page = 0;
        // get the maximum pages can print
        int max_pages = (totalIndex) / product_per_page;
        // check whether the prodcut been choosen or not
        boolean check = false;
        do {
            int count = 1;
            // get the start index and end index
            int startIndex = page * product_per_page;
            int endIndex = startIndex + product_per_page < totalIndex ? startIndex + product_per_page : totalIndex;
            // print the product within the index
            for (int i = startIndex; i < endIndex; i++) {
                System.out.printf("[%d] %-20s%-20s%-5d%-10.2f\n",
                        count,
                        productList.get(i).getSKU(),
                        productList.get(i).getName(),
                        productList.get(i).getQuantity(),
                        productList.get(i).getPrice());
                count++;
            }
            System.out.printf("Page %d of %d\n", page+1, max_pages + 1);
            System.out.printf("Total product: %d\n", totalIndex+1);
            System.out.println("[\"A\" for last page]\t[\"D\" for next page]");
            char input = scanner.nextLine().charAt(0);
            switch (input) {
                case 'A':
                case 'a':
                    if (page > 0)
                        page--;
                case 'D':
                case 'd':
                    if (page < max_pages)
                        page++;
                default:
                    if (Character.isDigit(input)){
                        int num = input = '0';
                        if (num<=5 && num>0){
                            product = productList.get(startIndex+num-1);
                            check = true;
                        }
                    }
            }
        }while(!check);
        return product;
    }


}
