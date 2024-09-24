class HelloWorld{
    public void printSum(){
        int a = 40;
        a = a+1;
        System.out.println(a);
    }
    public static void main(String[] args){
        HelloWorld obj = new HelloWorld();
        obj.printSum();
        int a = 40;
        int b = 30;
        int c = a+b;
        float d = 20.345f;
        System.out.println(d);
        System.out.println("The sum of a and b is:"+c);
        System.out.println("Hello World");
    }
}