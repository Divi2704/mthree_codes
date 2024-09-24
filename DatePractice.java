import java.util.Date;
import java.text.SimpleDateFormat;

class DatePractice {
    public static void main(String[] args){
        Date date = new Date();
        System.out.println("Date:"+date);
        System.out.println("Date:"+date.toString());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);
        System.out.println("Date:"+strDate);
    }

}
