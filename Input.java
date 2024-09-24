
import java.util.Scanner;
public class Input {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name:");
        String name = sc.nextLine();
        System.out.println("Hello,"+name+"!");

        System.out.println("Enter first number:");
        double num1 = sc.nextDouble();
        System.out.println("Enter second number:");
        double num2 = sc.nextDouble();
        char operation = sc.next().charAt(0);
        double result = 0;
        switch(operation){
            case'+':
            result = num1 + num2;
            break;
            case'-':
            result = num1 - num2; 
            break;
            case'*':
            result = num1 * num2;
            break;
            case'/':
            result = num1 / num2;
            break;
        }
        System.out.println("Result:"+result);
    }
}
