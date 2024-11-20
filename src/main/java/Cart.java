package PACKAGE_NAME;public class Cart {
}
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products;

    public Cart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void viewCart() {
        if (products.isEmpty()) {
            System.out.println("Кошик порожній.");
        } else {
            System.out.println("Товари в кошику:");
            double total = 0;
            for (Product product : products) {
                System.out.println(product);
                total += product.getPrice();
            }
            System.out.println("Загальна сума: " + total + " грн");
        }
    }

    public void clearCart() {
        products.clear();
    }
