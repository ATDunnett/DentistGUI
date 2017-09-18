import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;

public class Payment implements Serializable{
	
    //private static int paymentNoCounter;
    private int paymentNo;
    private double paymentAmt;
    //private Date paymentDate;
    //private SimpleDateFormat date;
    private String date;
    private DBConnection db;
    
    public Payment(int payNum, DBConnection database){
        this.db = database;
        this.paymentNo = payNum;
        this.paymentAmt = this.db.getDoubleWithInt("paymentAmount", "Payments", "paymentNumber", this.getPaymentNo());
        this.date = this.db.getStringDataWithInt("paymentDate", "Payments", "paymentNumber", this.getPaymentNo());
        //this.paymentNo = Payment.paymentNoCounter;
        //Payment.paymentNoCounter++;
        //this.date = new SimpleDateFormat("dd-MM-yyyy");
	}

    public int getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(int paymentNo) {
        this.paymentNo = paymentNo;
    }

    public double getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(double paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public String getPaymentDate() {
        //return this.date.format(paymentDate);
    return this.date;
    }

	
	
    public String toString(){
        return "Payment#\t: " + this.getPaymentNo()
            + "\nAmount\t\t: " + this.getPaymentAmt()
            + "\nDate\t\t: " + this.getPaymentDate()
        + "\n\n";
    }

    public void print(){
        System.out.println(this);
    }
}
