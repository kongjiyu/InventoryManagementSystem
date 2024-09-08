package DataAccessObject;

import DatabaseTools.ProductTools;
import Entity.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductDAO {
    static Scanner scanner = new Scanner(System.in);

    public static void createProduct() {
        Product product = new Product();
        //input product name
        inputName(product);

        //input product desc
        inputDesc(product);

        //input product category
        inputCategory(product);

        //input product price
        inputPrice(product);

        //input product weight
        inputWeight(product);

        //input product dimension
        inputDimension(product);

        //input product quantity
        inputQuantity(product);

        do{
            //confirm product information
            System.out.println("Product information:");
            System.out.println("1. Name: " + product.getName());
            System.out.println("2. Description: " + product.getDesc());
            System.out.println("3. Category: " + product.getCategory());
            System.out.println("4. Price: " + product.getPrice());
            System.out.println("5. Weight: " + product.getWeight());
            System.out.println("6. Dimension: ");
            System.out.println(product.getDimension().toString());
            System.out.println("7. Quantity: " + product.getQuantity());
            System.out.println();
            System.out.print("Are you sure the product information is correct? (y/n) : ");
            if (scanner.next().equalsIgnoreCase("y")) {
                ProductTools.insertProduct(product);
                break;
            } else {
                System.out.print("Select an option to modify: ");
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        inputName(product);
                        break;
                    case 2:
                        inputDesc(product);
                        break;
                    case 3:
                        inputCategory(product);
                        break;
                    case 4:
                        inputPrice(product);
                        break;
                    case 5:
                        inputWeight(product);
                        break;
                    case 6:
                        inputDimension(product);
                        break;
                    case 7:
                        inputQuantity(product);
                        break;
                    default:
                        System.out.println("Invalid option!");
                        break;
                }
            }
        }while(true);
    }

    public static void inputName(Product product) {
        System.out.println();
        System.out.print("Please enter product name: ");
        product.setName(scanner.nextLine());
    }

    public static void inputDesc(Product product) {
        System.out.println();
        System.out.print("Please enter product description: ");
        product.setDesc(scanner.nextLine());
    }

    public static void inputCategory(Product product) {
        //store all categories in an arraylist
        ArrayList<String> categories = ProductTools.retrieveAllCategories();
        if (!categories.isEmpty()) {
            do {
                System.out.println("To add new category please enter 0");
                System.out.println("List of all categories: ");

                //print all categories
                for (int i = 0; i < categories.size(); i++) {
                    System.out.println((i + 1) + ". " + categories.get(i));
                }

                System.out.print("Please choose an category: ");
                try {
                    //get user input
                    int categoryIndex = scanner.nextInt();
                    scanner.nextLine();

                    //add new category
                    if (categoryIndex == 0) {
                        System.out.print("Enter new category: ");
                        product.setCategory(scanner.nextLine());
                        break;

                        //out of range
                    } else if (categoryIndex < 0 || categoryIndex > categories.size()) {
                        System.out.println("Invalid input!");

                        //set category
                    } else {
                        product.setCategory(categories.get(categoryIndex - 1));
                        break;
                    }
                } catch (Exception e) {
                    //invalid input(not a number)
                    System.out.println("Invalid input!");
                    System.out.println();
                }
            } while (true);
        } else {
            System.out.print("Please enter category: ");
            product.setCategory(scanner.nextLine());
        }

    }

    public static void inputPrice(Product product) {
        do {
            System.out.println();
            System.out.print("Enter product price: ");
            try {
                product.setPrice(scanner.nextDouble());
                scanner.nextLine();
                if (product.getPrice() < 0) {
                    System.out.println("Invalid input!");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

    public static void inputWeight(Product product) {
        do {
            System.out.println();
            System.out.print("Enter product weight(kg): ");
            try {
                product.setWeight(scanner.nextDouble());
                scanner.nextLine();
                if (product.getWeight() < 0) {
                    System.out.println("Invalid input!");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

    public static void inputDimension(Product product) {
        do {
            try {
                System.out.println();
                System.out.print("Enter product length(cm): ");

                //set length
                product.getDimension().setLength(scanner.nextDouble());
                scanner.nextLine();

                //validate length
                if (product.getDimension().getLength() < 0) {
                    System.out.println("Invalid input!");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);

        do {
            try {
                System.out.println();
                System.out.print("Enter product width(cm): ");

                //set width
                product.getDimension().setWidth(scanner.nextDouble());
                scanner.nextLine();

                //validate width
                if (product.getDimension().getWidth() < 0) {
                    System.out.println("Invalid input!");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);

        do {
            try {
                System.out.println();
                System.out.print("Enter product height(cm): ");

                //set height
                product.getDimension().setHeight(scanner.nextDouble());
                scanner.nextLine();

                //validate height
                if (product.getDimension().getHeight() < 0) {
                    System.out.println("Invalid input!");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);

    }

    public static void inputQuantity(Product product) {
        do {
            try {
                System.out.println();
                System.out.print("Enter product quantity: ");
                product.setQuantity(scanner.nextInt());
                scanner.nextLine();
                if (product.getQuantity() < 0) {
                    System.out.println("Invalid input!");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

}
