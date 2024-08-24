package DataAccessObject;

import Entity.Product;

import java.util.Scanner;

public class ProductDAO {
    public static void createProduct() {
        Scanner scanner = new Scanner(System.in);

        Product product = new Product();
        //input product name
        System.out.print("Enter product name: ");
        product.setName(scanner.next());

        //input product desc
        System.out.print("Enter product description: ");
        product.setDesc(scanner.next());

        //input product category

        System.out.println();

        //input product price
        //input product weight
        //input product dimension
        //input product quantity

        //update product updated date

        //insert product into database
    }

}
