package DataAccessObject;

import java.util.Scanner;

public class PaymentDAO {
    static Scanner scanner = new Scanner(System.in);

    public boolean payByCash(double amount) {
        boolean paymentStatus = false;
        do {
            System.out.print("Enter payed amount (Enter 0 to exit): ");
            try {
                double payedAmount = scanner.nextDouble();
                scanner.nextLine();
                if (payedAmount == amount) {
                    System.out.println("Payment successful. No change.");
                    paymentStatus = true;
                } else if (payedAmount == 0) {
                    break;
                } else if (payedAmount < amount) {
                    System.out.println("Payment failed, insufficient amount.");
                } else {
                    System.out.println("Payment successful. Change: " + (payedAmount - amount));
                    paymentStatus = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!paymentStatus);

        return paymentStatus;
    }

//    public boolean payByCard(double amount){
//        boolean paymentStatus = false;
//        System.out.print("Enter card number (enter x to exit): ");
//        String cardNumber = scanner.nextLine();
//        if(cardNumber.equalsIgnoreCase("x")){
//
//        }
//    }
}
