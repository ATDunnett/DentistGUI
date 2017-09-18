import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Patient extends Person implements Serializable{
	
    //private static int patientNumberCounter = 0;
    private int patientNumber;
    //private ArrayList<Invoice> patientInvoiceList;

    private DBConnection db;
	
    public Patient(String newName, DBConnection database){ 
        super(newName, database.getStringData("patientAddress", "Patients", "patientName", newName));
        this.db = database;
        this.patientNumber = this.db.getIntData("patientNumber", "Patients", "patientName", newName);
		//this.patientNumber = Patient.patientNumberCounter;
		//Patient.patientNumberCounter++;
		//patientInvoiceList = new ArrayList<>();
    }
	
    public int getPatientNumber() {
        return this.patientNumber;
    }

    public ArrayList<Invoice> getPatientInvoiceList() {
        ArrayList<Invoice> invoiceList = new ArrayList<>();
        ArrayList<Integer> tempList = this.db.getIntDataColumnWithWhere("invoiceNumber", "Invoices", "invoicePatient", this.getPatientNumber());
        for(int i = 0; i < this.db.getListSizeWithInt("Invoices", "invoicePatient", this.getPatientNumber()); i++){
            invoiceList.add(new Invoice(tempList.get(i).intValue(),this.db));
        }
        return invoiceList;
    }

    public boolean isInvoiceListEmpty(){
        return this.db.isTableEmpty("Invoices");

    }

    public void addInvoice(Date date){
        //patientInvoiceList.add(new Invoice(newDate));
        this.db.addToInvoiceTable(date, this.getPatientNumber());
    }

    public void removeInvoice(int invNum){
        if(this.db.isTableEmpty("Invoices")){
            System.out.println("Patient invoice list is empty");
        }else{//patientInvoiceList.remove(index);}
        this.db.removeRowWithInt("Invoices", "invoiceNumber", invNum);}
    }
    
    public Invoice getInvoice(int invIndex){
        //Invoice invoice = new Invoice(invNum, this.db);
        //return invoice;
        return this.getPatientInvoiceList().get(invIndex);
    }
    
    public Payment getPayment(int invIndex, int payIndex){
        return this.getPatientInvoiceList().get(invIndex).getPayment(payIndex);
    }
    
    public Procedure getProcedure(int invIndex, int procIndex){
        return this.getPatientInvoiceList().get(invIndex).getProcedure(procIndex);
    }
    
    
    
    public double getRemainingBill(){
        double bill = 0;
        //for(int i = 0; i < this.patientInvoiceList.size(); i++){
        for(int i = 0; i < this.getPatientInvoiceList().size(); i++){
            bill += this.getInvoice(i).calcAmountRemaining();
        }
        return bill;
    }
	
    public Invoice findInvoiceByNum(int index){
        Invoice inv = null;
        for(int i = 0; i < this.getPatientInvoiceList().size(); i++){
            if(index == this.getInvoice(i).getInvoiceNo()){
                inv = this.getInvoice(i);
            }
        }
        return inv;
    }
    
	
    public String toString(){
        return "\t*****" +
            "\nPatient#: " + this.patientNumber +
            "\nName\t: " + super.getName() + 
            "\nAddress\t: " + super.getAddress() +
            "\n";
    }

    public void print(){
        System.out.println(this);
    }

    
	

}
