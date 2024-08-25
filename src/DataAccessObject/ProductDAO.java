package DataAccessObject;

import DatabaseTools.ProductTools;
import Entity.Product;

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


        //input product quantity

        //update product updated date

        //insert product into database
    }

    public static void inputName(Product product){
        System.out.println();
        System.out.print("Please enter product name: ");
        product.setName(scanner.next());
    }

    public static void inputDesc(Product product){
        System.out.println();
        System.out.print("Please enter product description: ");
        product.setDesc(scanner.next());
    }

    public static void inputCategory(Product product){
        //store all categories in an arraylist
        ArrayList<String> categories = ProductTools.retrieveAllCategories();

        do{
            System.out.println("To add new category please enter 0");
            System.out.println("List of all categories: ");

            //print all categories
            for(int i = 0; i < categories.size(); i++) {
                System.out.println((i+1) + ". " + categories.get(i));
            }

            System.out.print("Please choose an category: ");
            try{
                //get user input
                System.out.println();
                int categoryIndex = scanner.nextInt();
                scanner.nextLine();

                //add new category
                if(categoryIndex == 0){
                    System.out.println("Enter new category: ");
                    product.setCategory(scanner.nextLine());

                //out of range
                }else if(categoryIndex < 0 || categoryIndex > categories.size()){
                    System.out.println("Invalid input!");

                //set category
                }else{
                    product.setCategory(categories.get(categoryIndex-1));
                    break;
                }
            } catch (Exception e){
                //invalid input(not a number)
                System.out.println("Invalid input!");
                System.out.println();
            }
        }while(true);
    }

    public static void inputPrice(Product product){
        do{
            System.out.println();
            System.out.print("Enter product price: ");
            try{
                System.out.println();
                product.setPrice(scanner.nextDouble());
                scanner.nextLine();
                if(product.getPrice() < 0){
                    System.out.println("Invalid input!");
                }else{
                    break;
                }
            }catch (Exception e){
                System.out.println("Invalid input!");
            }
        }while(true);
    }

    public static void inputWeight(Product product){
        do{
            System.out.println();
            System.out.print("Enter product weight(kg): ");
            try{
                product.setWeight(scanner.nextDouble());
                scanner.nextLine();
                if(product.getWeight() < 0){
                    System.out.println("Invalid input!");
                }else{
                    break;
                }
            }catch (Exception e){
                System.out.println("Invalid input!");
            }
        }while(true);
    }

    public static void inputDimension(Product product){
        do{
            try{
                System.out.println();
                System.out.print("Enter product length(cm): ");

                //set length
                product.getDimension().setLength(scanner.nextDouble());
                scanner.nextLine();

                //validate length
                if(product.getDimension().getLength() < 0){
                    System.out.println("Invalid input!");
                }
            }catch (Exception e){
                System.out.println("Invalid input!");
            }

            try{
                System.out.println();
                System.out.print("Enter product width(cm): ");

                //set width
                product.getDimension().setWidth(scanner.nextDouble());
                scanner.nextLine();

                //validate width
                if(product.getDimension().getWidth() < 0){
                    System.out.println("Invalid input!");
                }
            }catch (Exception e){
                System.out.println("Invalid input!");
            }

            try{
                System.out.println();
                System.out.print("Enter product height(cm): ");

                //set height
                product.getDimension().setHeight(scanner.nextDouble());
                scanner.nextLine();

                //validate height
                if(product.getDimension().getHeight() < 0){
                    System.out.println("Invalid input!");
                }else{
                    break;
                }
            }catch (Exception e){
                System.out.println("Invalid input!");
            }
        }while(true);
    }

}
