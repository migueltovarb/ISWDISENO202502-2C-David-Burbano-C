package supermarket;

import java.util.Scanner;

public class SupermarketInventory {

    // Constant for the maximum number of products
    public static final int MAX_PRODUCTS = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Arrays to store product names and quantities
        String[] productNames = new String[MAX_PRODUCTS];
        int[] productQuantities = new int[MAX_PRODUCTS];

        // Step 1: Register products and their quantities
        System.out.println("=== Supermarket Inventory Registration ===");
        for (int i = 0; i < MAX_PRODUCTS; i++) {
            System.out.print("Enter product name [" + (i + 1) + "]: ");
            productNames[i] = scanner.nextLine();

            System.out.print("Enter product quantity for " + productNames[i] + ": ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            productQuantities[i] = quantity;
        }

        int option;
        do {
            System.out.println("\n=== Supermarket Inventory Menu ===");
            System.out.println("1. Show all products and quantities");
            System.out.println("2. Search product by name");
            System.out.println("3. Update product stock (increase or decrease)");
            System.out.println("4. Show alerts for products with quantity < 10");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    showInventory(productNames, productQuantities);
                    break;

                case 2:
                    System.out.print("Enter product name to search: ");
                    String searchName = scanner.nextLine();
                    searchProduct(productNames, productQuantities, searchName);
                    break;

                case 3:
                    System.out.print("Enter product name to update: ");
                    String updateName = scanner.nextLine();
                    updateStock(scanner, productNames, productQuantities, updateName);
                    break;

                case 4:
                    showLowStockAlerts(productNames, productQuantities);
                    break;

                case 5:
                    System.out.println("Exiting the system...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (option != 5);

        scanner.close();
    }

    // Show all products and total inventory
    public static void showInventory(String[] names, int[] quantities) {
        System.out.println("\n--- Current Inventory ---");
        int totalProducts = 0;
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i] + ": " + quantities[i] + " units");
            totalProducts += quantities[i];
        }
        System.out.println("Total products in inventory: " + totalProducts);
    }

    // Search for a product by name
    public static void searchProduct(String[] names, int[] quantities, String searchName) {
        boolean found = false;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equalsIgnoreCase(searchName)) {
                System.out.println("Product found: " + names[i] + " has " + quantities[i] + " units.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found in inventory.");
        }
    }

    // Update stock with validations
    public static void updateStock(Scanner scanner, String[] names, int[] quantities, String updateName) {
        boolean found = false;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equalsIgnoreCase(updateName)) {
                found = true;
                System.out.println("Current stock for " + names[i] + ": " + quantities[i] + " units");
                System.out.print("Do you want to increase or decrease stock? (type 'increase' or 'decrease'): ");
                String action = scanner.nextLine();

                if (action.equalsIgnoreCase("increase")) {
                    System.out.print("Enter units to add: ");
                    int add = scanner.nextInt();
                    scanner.nextLine();
                    quantities[i] += add;
                    System.out.println("Stock updated successfully!");
                } else if (action.equalsIgnoreCase("decrease")) {
                    System.out.print("Enter units to remove: ");
                    int remove = scanner.nextInt();
                    scanner.nextLine();
                    if (remove <= quantities[i]) {
                        quantities[i] -= remove;
                        System.out.println("Stock updated successfully!");
                    } else {
                        System.out.println("Error: You cannot remove more than current stock.");
                    }
                } else {
                    System.out.println("Invalid action.");
                }
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found in inventory.");
        }
    }

    // Show products with quantity < 10
    public static void showLowStockAlerts(String[] names, int[] quantities) {
        System.out.println("\n--- Low Stock Alerts (less than 10) ---");
        boolean alert = false;
        for (int i = 0; i < names.length; i++) {
            if (quantities[i] < 10) {
                System.out.println("Alert: " + names[i] + " has only " + quantities[i] + " units left!");
                alert = true;
            }
        }
        if (!alert) {
            System.out.println("All products have sufficient stock.");
        }
    }
}
