public class staticExample {
    static int count = 0;
    int instanceNumber;
    public staticExample(){
        count++;
        instanceNumber = count;
    }
    public void display(){
        System.out.println("Instance Number:"+instanceNumber+"\n"+"Count:"+count);
    }
    public static void main(String[] args){
        staticExample obj1 = new staticExample();
        staticExample obj2 = new staticExample();
        staticExample obj3 = new staticExample();
        obj1.display();
        obj2.display();
        obj3.display();
    }
}
