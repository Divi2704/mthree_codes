import java.util.Scanner;
/**
 * Interface for online shopping functionality.
 * This interface defines the contract for classes that represent products in an online shopping system.
 */
interface OnlineShoppingInterface {
    /**
     * Displays the details of a product.
     * Implementing classes should provide their own implementation to show product-specific information.
     */
    void displayProductDetails();
}

/**
 * Abstract class representing a generic product in the online shopping system.
 * This class implements the OnlineShoppingInterface and provides common functionality for all products.
 */
abstract class Product implements OnlineShoppingInterface {
    private int productId;
    private String productName;
    private double price;

    /**
     * Constructor for creating a new Product.
     * @param productId The unique identifier for the product.
     * @param productName The name of the product.
     * @param price The price of the product.
     */
    public Product(int productId, String productName, double price){
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    /**
     * Getter for product ID.
     * @return The product ID.
     */
    public int getProductId(){
        return productId;
    }

    /**
     * Getter for product name.
     * @return The product name.
     */
    public String getProductName(){
        return productName;
    }

    /**
     * Getter for product price.
     * @return The product price.
     */
    public double getPrice(){
        return price;
    }

    /**
     * Abstract method to calculate the discount for the product.
     * @return The discount amount.
     */
    public abstract double calculateDiscount();

    /**
     * Displays the details of the product including ID, name, price, discount, and final price.
     */
    public void displayProductDetails(){
        System.out.println("Product ID:" + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("Price:" + price);
        System.out.println("Discount:" + calculateDiscount());
        System.out.println("Price after Discount:" + (price - calculateDiscount()));
    }
}

/**
 * Class representing electronic products.
 */
class Electronics extends Product{
    /**
     * Constructor for creating a new Electronics product.
     * @param productId The unique identifier for the product.
     * @param productName The name of the product.
     * @param price The price of the product.
     */
    public Electronics(int productId, String productName, double price){
        super(productId, productName, price);
    }

    /**
     * Calculates the discount for electronic products (10% of the price).
     * @return The discount amount.
     */
    @Override
    public double calculateDiscount(){
        return getPrice() * 0.10;
    }
}

/**
 * Class representing clothing products.
 */
class Clothing extends Product {
    /**
     * Constructor for creating a new Clothing product.
     * @param productId The unique identifier for the product.
     * @param productName The name of the product.
     * @param price The price of the product.
     */
    public Clothing(int productId, String productName, double price){
        super(productId, productName, price);
    }

    /**
     * Calculates the discount for clothing products (20% of the price).
     * @return The discount amount.
     */
    @Override
    public double calculateDiscount(){
        return getPrice() * 0.20;
    }
}

/**
 * Class representing grocery products.
 */
class Grocery extends Product {
    /**
     * Constructor for creating a new Grocery product.
     * @param productId The unique identifier for the product.
     * @param productName The name of the product.
     * @param price The price of the product.
     */
    public Grocery(int productId, String productName, double price) {
        super(productId, productName, price);
    }

    /**
     * Calculates the discount for grocery products (5% of the price).
     * @return The discount amount.
     */
    @Override
    public double calculateDiscount() {
        return getPrice() * 0.05; 
    }
}

/**
 * Class representing a shopping cart.
 */
class ShoppingCart{
    private Product[] cartProducts;
    private int productCount;

    /**
     * Constructor for creating a new ShoppingCart with a capacity of 20 products.
     */
    public ShoppingCart(){
        cartProducts = new Product[20];
        productCount = 0;
    }

    /**
     * Adds a product to the shopping cart.
     * @param product The product to be added.
     */
    public void addProduct(Product product){
        if (productCount < cartProducts.length){
            cartProducts[productCount] = product;
            productCount++;
            System.out.println(product.getProductName() + " added to cart.");
        } else {
            System.out.println("Cart is full. Cannot add more items.");
        }
    }

    /**
     * Displays the contents of the shopping cart.
     */
    public void displayCart(){
        if(productCount == 0){
            System.out.println("Cart is empty.");
        }
        else{
            System.out.println("Cart products:");
            for(int i=0; i< productCount;i++){
                cartProducts[i].displayProductDetails();
                System.out.println();
            }
        }
    }

    /**
     * Calculates the total cost of all items in the cart after applying discounts.
     * @return The total cost.
     */
    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < productCount; i++) {
            total += cartProducts[i].getPrice() - cartProducts[i].calculateDiscount();
        }
        return total;
    }
}

/**
 * Main class for the Online Shopping application.
 */
public class OnlineShopping {
    /**
     * Main method to run the Online Shopping application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        // Create sample products
        Product laptop = new Electronics(101, "Laptop", 1000.00);
        Product phone = new Electronics(102, "Smartphone", 500.00);
        Product tshirt = new Clothing(201, "T-shirt", 20.00);
        Product jeans = new Clothing(202, "Jeans", 40.00);
        Product apple = new Grocery(301, "Apple", 30.00);
        Product banana = new Grocery(302,"Banana", 15.00);

        boolean shopping = true;
        while (shopping) {
            // Display menu options
            System.out.println("\n1. Add Laptop to Cart");
            System.out.println("2. Add Smartphone to Cart");
            System.out.println("3. Add T-shirt to Cart");
            System.out.println("4. Add Jeans to Cart");
            System.out.println("5. Add Apple to Cart");
            System.out.println("6.Add Banana to Cart");
            System.out.println("7. View Cart");
            System.out.println("8. Checkout and Exit");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();

            // Process user's choice
            switch (option) {
                case 1:
                    cart.addProduct(laptop);
                    break;
                case 2:
                    cart.addProduct(phone);
                    break;
                case 3:
                    cart.addProduct(tshirt);
                    break;
                case 4:
                    cart.addProduct(jeans);
                    break;
                case 5:
                    cart.addProduct(apple);
                    break;
                case 6:
                    cart.addProduct(banana);
                    break;
                case 7:
                    cart.displayCart();
                    break;
                case 8:
                    double total = cart.calculateTotal();
                    System.out.println("Total amount to be paid after discounts: $" + total);
                    shopping = false;
                    System.out.println("Thank you for shopping!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        sc.close();
    }
}