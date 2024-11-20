package PACKAGE_NAME;public class Category {
}
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Product class - describes a product
class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: " + price + " UAH";
    }
}

// Category class - describes a product category
class Category {
    private int id;
    private String name;
    private List<Product> products;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.products = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Category: " + name + " (ID: " + id + ")";
    }
}

// Cart class - manages the shopping cart
class Cart {
    List<Product> products;

    public Cart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void viewCart() {
        if (products.isEmpty()) {
            System.out.println("The cart is empty.");
            return;
        }
        System.out.println("\n=== Products in the cart ===");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println("Total cost: " + calculateTotal() + " UAH");
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public double calculateTotal() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clearCart() {
        products.clear();
    }
}

// Order class - manages orders
class Order {
    private static int nextOrderId = 1;
    private int orderId;
    private List<Product> products;
    private double totalPrice;

    public Order() {
        this.orderId = nextOrderId++;
        this.products = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public void addProduct(Product product) {
        products.add(product);
        totalPrice += product.getPrice();
    }

    public void displayOrderDetails() {
        System.out.println("\n=== Order Details #" + orderId + " ===");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println("Total cost: " + totalPrice + " UAH");
    }

    public int getOrderId() {
        return orderId;
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Category> categories = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        Cart cart = new Cart();

        // Create categories and products
        Category electronics = new Category(1, "Electronics");
        electronics.addProduct(new Product(1, "Laptop", 25000));
        electronics.addProduct(new Product(2, "Smartphone", 15000));
        electronics.addProduct(new Product(3, "Headphones", 2000));
        electronics.addProduct(new Product(4, "Monitor", 8000));

        Category clothing = new Category(2, "Clothing");
        clothing.addProduct(new Product(5, "T-shirt", 500));
        clothing.addProduct(new Product(6, "Jeans", 1500));

        categories.add(electronics);
        categories.add(clothing);

        // Main menu
        while (true) {
            System.out.println("\n=== Online Store ===");
            System.out.println("1. View Catalog");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Place an Order");
            System.out.println("5. View Order History");
            System.out.println("6. Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n=== Product Catalog ===");
                    for (Category category : categories) {
                        System.out.println(category);
                        for (Product product : category.getProducts()) {
                            System.out.println("  " + product);
                        }
                    }
                    break;
                case 2:
                    System.out.println("\n=== Select a Category ===");
                    for (Category category : categories) {
                        System.out.println(category.getId() + ". " + category.getName());
                    }
                    System.out.print("Enter the category ID: ");
                    int categoryId = scanner.nextInt();

                    Category selectedCategory = categories.stream()
                            .filter(c -> c.getId() == categoryId)
                            .findFirst()
                            .orElse(null);

                    if (selectedCategory != null) {
                        System.out.println("\nProducts in category: " + selectedCategory.getName());
                        for (Product product : selectedCategory.getProducts()) {
                            System.out.println(product);
                        }
                        System.out.print("\nEnter the product ID to add to the cart: ");
                        int productId = scanner.nextInt();

                        Product productToAdd = selectedCategory.getProducts().stream()
                                .filter(p -> p.getId() == productId)
                                .findFirst()
                                .orElse(null);

                        if (productToAdd != null) {
                            cart.addProduct(productToAdd);
                            System.out.println("Product added to cart: " + productToAdd.getName());
                        } else {
                            System.out.println("Product with this ID not found.");
                        }
                    } else {
                        System.out.println("Category with this ID not found.");
                    }
                    break;
                case 3:
                    cart.viewCart();
                    break;
                case 4:
                    if (cart.isEmpty()) {
                        System.out.println("The cart is empty. Add products first.");
                    } else {
                        Order order = new Order();
                        for (Product product : cart.products) {
                            order.addProduct(product);
                        }
                        orders.add(order);
                        order.displayOrderDetails();
                        cart.clearCart();
                        System.out.println("Order successfully placed!");
                    }
                    break;
                case 5:
                    if (orders.isEmpty()) {
                        System.out.println("Order history is empty.");
                    } else {
                        for (Order order : orders) {
                            order.displayOrderDetails();
                        }
                    }
                    break;
                case 6:
                    System.out.println("Thank you for using the online store!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
