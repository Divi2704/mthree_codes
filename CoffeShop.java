import java.util.Scanner;
public class CoffeShop {
    public enum CoffeeSize{
        SMALL(3.5, 8),
        MEDIUM(4.3, 15),
        LARGE(4.6, 20),
        EXTRA_LARGE(5.0, 25);

        private final double price;
        private final int ounces;

        CoffeeSize(double price, int ounces){
            this.price = price;
            this.ounces = ounces;
        }
        public double getPrice(){
            return price;
        }
        public int getOunces(){
            return ounces;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to our CoffeeShop!");
        System.out.println("Available sizes: ");
        for(CoffeeSize size: CoffeeSize.values()){
            System.out.printf("%s(%d oz)-$%.2f%n",
            size.name(),
            size.getOunces(),
            size.getPrice());
        }
        System.out.println("Enter your choice(SMALL/MEDIUM/LARGE/EXTRA_LARGE):");
        String userChoice = scanner.nextLine().toUpperCase();
        try{
            CoffeeSize selectedSize = CoffeeSize.valueOf(userChoice);
            System.out.printf("You've selected a %s coffee(%d oz). %n", selectedSize.name(), selectedSize.getOunces());
            System.out.printf("Price:$%2f%n",selectedSize.getPrice());
        }
        catch(IllegalArgumentException e){
            System.out.println("Invalid size selected. Please try again.");
        }
        scanner.close();
    }
}
