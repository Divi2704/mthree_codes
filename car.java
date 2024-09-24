class Vehicle {}
    public class car extends Vehicle{
        public static void main(String[] args) {
            Vehicle a = new car();
            boolean result = a instanceof car;
            System.out.println("result ="+result);
        }
    }
