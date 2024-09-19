package DataAccessObject;

import DatabaseTools.ProductTools;
import Driver.Utils;
import Entity.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductDAO {
    static Scanner scanner = new Scanner(System.in);

    //Create
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

        do{
            //confirm product information
            System.out.println("Product information:");
            System.out.println("[1] Name: " + product.getName());
            System.out.println("[2] Description: " + product.getDesc());
            System.out.println("[3] Category: " + product.getCategory());
            System.out.println("[4] Price: " + product.getPrice());
            System.out.println("[5] Weight: " + product.getWeight());
            System.out.println("[6] Dimension: ");
            System.out.println(product.getDimension().toString());
            System.out.println();
            System.out.print("Are you sure the product information is correct? (y/n) : ");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                ProductTools.insertProduct(product);
                break;
            } else if (answer.equalsIgnoreCase("n")) {
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
                    default:
                        System.out.println("Invalid option!");
                        try {
                            Thread.sleep(500);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        break;
                }
            }else {
                System.out.println("Invalid input!");
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
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
                        try {
                            Thread.sleep(500);
                        }catch (InterruptedException ie){
                            ie.printStackTrace();
                        }

                        //set category
                    } else {
                        product.setCategory(categories.get(categoryIndex - 1));
                        break;
                    }
                } catch (Exception e) {
                    //invalid input(not a number)
                    System.out.println("Invalid input!");
                    System.out.println();
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
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
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
                try {
                    Thread.sleep(500);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
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
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
                try {
                    Thread.sleep(500);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
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
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
                try {
                    Thread.sleep(500);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
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

    //Read
    public static void displayAllProduct(){
        ArrayList<Product> products = ProductTools.retrieveAllProducts();

        if (products.isEmpty()) {
            System.out.println("No products found!");
            try {
                Thread.sleep(500);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            int totalProducts = products.size();
            final int productsPerPage = 5;  // Number of products to show per page
            int page = 0;
            int maxPages = (totalProducts - 1) / productsPerPage;
            boolean exit = false;

            do {
                int startIndex = page * productsPerPage;
                int endIndex = Math.min(startIndex + productsPerPage, totalProducts);

                // Display the current page of products
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("List of all products:");
                System.out.println("===================================================================================================================");
                System.out.printf("|%-5s|%-50s|%-20s|%-7s|%-27s|\n", "UPC", "Name", "Category", "Weight", "Dimension");
                System.out.println("===================================================================================================================");

                for (int i = startIndex; i < endIndex; i++) {
                    Product product = products.get(i);
                    System.out.printf("|%-5d|%-50s|%-20s|%-5.2fkg|%5.2fcm x %5.2fcm x %5.2fcm|\n",
                            product.getUPC(),
                            product.getName(),
                            product.getCategory(),
                            product.getWeight(),
                            product.getDimension().getLength(),
                            product.getDimension().getWidth(),
                            product.getDimension().getHeight());
                }

                System.out.println("===================================================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total products: %d\n", totalProducts);
                System.out.println("[\"A\" for previous page]\t\t[\"Q\" to exit]\t\t[\"D\" for next page]");
                System.out.print("Select navigation option: ");

                String input = scanner.nextLine().trim();

                if (input.length() == 1) {
                    char option = input.charAt(0);

                    switch (option) {
                        case 'A':
                        case 'a':
                            if (page > 0) page--;
                            break;
                        case 'D':
                        case 'd':
                            if (page < maxPages) page++;
                            break;
                        case 'Q':
                        case 'q':
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid input. Please enter 'A', 'D', or 'Q'.");
                            try {
                                Thread.sleep(500);
                            }catch (InterruptedException ie){
                                ie.printStackTrace();
                            }
                    }
                } else {
                    System.out.println("Invalid input. Please enter a single character.");
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                }

            } while (!exit);
        }
    }

    //Delete
    public static void deleteProduct(){
        int inputUPC = Utils.getIntInput("Please enter product UPC to delete: ");
        if(ProductTools.deleteProduct(inputUPC)){
            System.out.println("Product deleted successfully!");
        }else{
            System.out.println("Product Not Found!");
        }

        try {
            Thread.sleep(500);
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

    //Update
    public static void updateProduct() {
        int option = 999;

            int inputUPC = Utils.getIntInput("Please enter product UPC to update: ");
            Product product = ProductTools.retrieveProduct(inputUPC);
            if(product == null){
                try {
                    Thread.sleep(500);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
                return;
            }

            do{
                System.out.println("Product information:");
                System.out.println("[1] Name: " + product.getName());
                System.out.println("[2] Description: " + product.getDesc());
                System.out.println("[3] Category: " + product.getCategory());
                System.out.println("[4] Price: " + product.getPrice());
                System.out.println("[5] Weight: " + product.getWeight());
                System.out.println("[6] Dimension: ");
                System.out.println(product.getDimension().toString());
                System.out.println();
                option = Utils.getIntInput("Select an option to modify or type 0 to exit: ");
                switch(option){
                    case 0:
                        ProductTools.updateProduct(product);
                        System.out.println("Update successful!");
                        try {
                            Thread.sleep(500);
                        }catch (InterruptedException ie){
                            ie.printStackTrace();
                        }
                        break;
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
                    default:
                        System.out.println("Invalid option!");
                        try {
                            Thread.sleep(500);
                        }catch (InterruptedException ie){
                            ie.printStackTrace();
                        }
                        break;
                }
            }while(option != 0);
    }

    //Search
    public static void searchProduct() {
        String searchField = "";
        String searchValue = "";
        double minValue = 0;
        double maxValue = 0;

        System.out.println("Search Product By:");
        System.out.println("1. UPC");
        System.out.println("2. Name");
        System.out.println("3. Category");
        System.out.println("4. Price Range");
        System.out.println("5. Weight Range");
        System.out.println("6. Quantity Range");
        System.out.print("Select an option: ");

        int option = Utils.getIntInput("");

        switch (option) {
            case 1:
                searchField = "ProductUPC";
                System.out.print("Enter Product UPC: ");
                searchValue = scanner.nextLine();
                break;
            case 2:
                searchField = "ProductName";
                System.out.print("Enter Product Name: ");
                searchValue = scanner.nextLine();
                break;
            case 3:
                searchField = "ProductCategory";
                System.out.print("Enter Product Category: ");
                searchValue = scanner.nextLine();
                break;
            case 4:
                searchField = "ProductPrice";
                System.out.print("Enter Minimum Price: ");
                minValue = Utils.getDoubleInput("");
                System.out.print("Enter Maximum Price: ");
                maxValue = Utils.getDoubleInput("");
                break;
            case 5:
                searchField = "ProductWeight";
                System.out.print("Enter Minimum Weight: ");
                minValue = Utils.getDoubleInput("");
                System.out.print("Enter Maximum Weight: ");
                maxValue = Utils.getDoubleInput("");
                break;
            default:
                System.out.println("Invalid option!");
                try {
                    Thread.sleep(500);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
                return;
        }

        ArrayList<Product> products;

        if (option == 4 || option == 5) {
            products = ProductTools.searchProductsByRange(searchField, minValue, maxValue);
        } else {
            products = ProductTools.searchProducts(searchField, searchValue);
        }

        if (products.isEmpty()) {
            System.out.println("No products found!");
            try {
                Thread.sleep(500);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            int totalProducts = products.size();
            final int productsPerPage = 5;  // Number of products to show per page
            int page = 0;
            int maxPages = (totalProducts - 1) / productsPerPage;
            boolean exit = false;

            do {
                int startIndex = page * productsPerPage;
                int endIndex = Math.min(startIndex + productsPerPage, totalProducts);

                // Display the current page of search results
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("Search Results:");
                System.out.println("==================================================================");
                System.out.printf("|%-5s|%-20s|%-15s|%-10s|%-10s|\n", "UPC", "Name", "Category", "Price", "Weight");
                System.out.println("==================================================================");

                for (int i = startIndex; i < endIndex; i++) {
                    Product product = products.get(i);
                    System.out.printf("|%-5d|%-20s|%-15s|%-10.2f|%-10.2f|\n",
                            product.getUPC(),
                            product.getName(),
                            product.getCategory(),
                            product.getPrice(),
                            product.getWeight());
                }

                System.out.println("==================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total products: %d\n", totalProducts);
                System.out.println("[\"A\" for previous page]\t\t[\"Q\" to exit]\t\t[\"D\" for next page]");
                System.out.print("Select navigation option: ");

                String input = scanner.nextLine().trim();

                if (input.length() == 1) {
                    char optionChar = input.charAt(0);

                    switch (optionChar) {
                        case 'A':
                        case 'a':
                            if (page > 0) page--;
                            break;
                        case 'D':
                        case 'd':
                            if (page < maxPages) page++;
                            break;
                        case 'Q':
                        case 'q':
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid input. Please enter 'A', 'D', or 'Q'.");
                            try {
                                Thread.sleep(500);
                            }catch (InterruptedException ie){
                                ie.printStackTrace();
                            }
                    }
                } else {
                    System.out.println("Invalid input. Please enter a single character.");
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                }

            } while (!exit);
        }

    }

    //Menu
    public static void productMenu(){
        int option = 999;

        do{
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Manage Product");
            System.out.println("=======================");
            System.out.println("[1] Display All Product");
            System.out.println("[2] Search product");
            System.out.println("[3] Delete product");
            System.out.println("[4] Update product");
            System.out.println("[5] Create new product");
            System.out.println("[6] Exit");
            System.out.println("=======================");

            option = Utils.getIntInput("Please select an option: ");
            switch(option){
                case 1:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    displayAllProduct();
                    break;
                case 2:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    searchProduct();
                    break;
                case 3:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    deleteProduct();
                    break;
                case 4:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    updateProduct();
                    break;
                case 5:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    createProduct();
                    break;
                case 6:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    break;
                default:
                    System.out.println("Invalid option!");
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
            }

        }while(option != 6);
    }


}
