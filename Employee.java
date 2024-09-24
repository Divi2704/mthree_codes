public class Employee {
    public String name;
    public int age;
    public String city;
    public double salary;
    public String companyName;
    public String employeeId;
    public String department;
    public void printDetails(){
        System.out.println("Name:"+name);
        System.out.println("Age:"+age);
        System.out.println("City:"+city);
        System.out.println("Salary:"+salary);
        System.out.println("Companyname:"+companyName);
        System.out.println("EmployeeId:"+employeeId);
        System.out.println("Department:"+department);
    }
    public void setDetails(String name, int age, String city, double salary, String companyName, String employeeId, String department){
        this.name = name;
        this.age = age;
        this.city = city;
        this.salary = salary;
        this.companyName = companyName;
        this.employeeId = employeeId;
        this.department = department;
    }
    public Employee(){
        name = "Divija";
        age = 21;
        city = "Hyderabad";
        salary = 10000;
        companyName = "ABC";
        employeeId = "12345678";
        department = "IT";
        printDetails();
    }
    public Employee(String name, int age, String city, double salary, String companyName, String employeeId, String department){
        this.name = name;
        this.age = age;
        this.city = city;
        this.salary = salary;
        this.companyName = companyName;
        this.employeeId = employeeId;
        this.department = department;
    }
    static double totalSalary;
        public static void main(String[] args){
            totalSalary = 30000;
            Employee obj = new Employee();
            obj.setDetails("Jasa",30,"Delhi",20000,"XYZ","23456789","HR");
            obj.printDetails();
            //Employee obj2 = new Employee("Jasa",30,"Delhi",20000,"XYZ","23456789","HR");
            //obj2.printDetails();
        }
    }
