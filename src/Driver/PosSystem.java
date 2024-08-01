package Driver;

import DatabaseTools.OrderTools;
import DatabaseTools.ProductTools;
import DatabaseTools.DiscountTools;
import Entity.Discount;
import Entity.Order;
import Entity.Product;

import java.util.Scanner;

public class PosSystem {
    static Scanner scanner = new Scanner(System.in);

    public static void buyItem() {
        //Variable declaration
        Order order = new Order(OrderTools.getNewOrderID());
        Product product = null;
        Discount discount = null;
        int quantity;
        boolean nextItem = true, getDiscount = false;

        //Loop to get product
        while (nextItem) {
            //Get Product SKU
            System.out.print("Please scan or enter product barcode: ");
            String barcode = scanner.nextLine();
            product = ProductTools.retrieveProduct(barcode);

            //Get Quantity
            System.out.print("Please enter quantity: ");
            try {
                quantity = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Something went wrong");
                continue;
            }

            //place into the cart
            order.setCart(product, quantity);
            order.setPrice(product.getPrice() * quantity);

            //Loop confirmation
            System.out.println("Do you want to add more items? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("n")) {
                nextItem = false;
            }
        }

        //After finish get product and quantity, check discount
        do{
            System.out.print("Is there any discount code? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                System.out.print("Enter discount code: ");
                String discountCode = scanner.nextLine();
                discount = DiscountTools.retrieveRate(discountCode);
                if(discount == null){
                    System.out.println("Invalid discount code");
                }else{
                    getDiscount = true;
                }
            }else{
                break;
            }
        }while(!getDiscount);

        //payment
        System.out.println("Please select payment method: ");
        System.out.println("1. Card");
        System.out.println("2. Cash");
        System.out.print("> ");


    }
}
