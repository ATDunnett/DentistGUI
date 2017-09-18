import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Invoice implements Serializable{
	
	//private static int invoiceNoCounter;
	private int invoiceNo;
	private double invoiceAmt;
	private String date;
	private boolean isPaid;
	//private ArrayList<Procedure> procList;
	//private ArrayList<Payment> paymentList;
	//private SimpleDateFormat date;
    private DBConnection db;
    
    public Invoice(int invNum, DBConnection database){
        this.db = database;
        this.date = this.db.getStringDataWithInt("invoiceDate", "Invoices", "invoiceNumber", invNum);
        this.invoiceNo = invNum;
        //Invoice.invoiceNoCounter++;
        //this.procList = new ArrayList<>();
        //this.paymentList =  new ArrayList<>();
        this.isPaid = this.db.getBooleanWithInt("invoicePaid", "Invoices", "invoiceNumber", invNum);
        //this.date = new SimpleDateFormat("dd-MM-yyyy");
        
    }
	
    public int getInvoiceNo() {
        return invoiceNo;
    }

	

	public double getInvoiceAmt() { //needs doing
        invoiceAmt = 0;
        for(int i = 0; i < this.getProcList().size(); i++){
            invoiceAmt += this.getProcedure(i).getProcCost();
        } 
		return invoiceAmt;
	}

	public void setInvoiceAmt(double invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public String getInvoiceDate() {
		//return this.date.format(invoiceDate);
        return this.date.toString();
	}

	public boolean isPaid() {
		return this.isPaid = this.db.getBooleanWithInt("invoicePaid", "Invoices", "invoiceNumber", this.getInvoiceNo());
	}

	public void setPaid(boolean isPaid) {
		this.db.updatePaid(isPaid, this.getInvoiceNo());
	}

	public ArrayList<Procedure> getProcList() {
        ArrayList<Procedure> procList = new ArrayList<>();
        ArrayList<Integer> tempList = this.db.getIntDataColumnWithWhere("procedureNumber", "Procedures", "procedureInvoice", this.getInvoiceNo());
        for(int i = 0; i < tempList.size(); i++){
            procList.add(new Procedure(tempList.get(i).intValue(), this.db));
        }
		return procList;
	}
    
    

	public ArrayList<Payment> getPaymentList() {
        ArrayList<Payment> paymentList = new ArrayList<>();
        ArrayList<Integer> tempList = this.db.getIntDataColumnWithWhere("paymentNumber", "Payments", "paymentInvoice", this.getInvoiceNo());
        for(int i = 0; i < tempList.size(); i++){
            paymentList.add(new Payment(tempList.get(i).intValue(),this.db));
        }
		return paymentList;
	}

	
	
	public boolean isInProcListEmpty(){
		//return procList.isEmpty();
        return this.db.isTableEmpty("Procedures");
	}
	
	public void addProcedure(ProcedureType type){
		//this.procList.add(new Procedure(type));
        this.db.addToProcedureTable(type.getName(), type.getCost(), this.getInvoiceNo());
    }
	
	public void removeProcedure(int procNum){
		if(this.db.isTableEmpty("Procedures")){
			System.out.println("Invoice procedure list is empty");
		}//else{procList.remove(index);}
        else{this.db.removeRowWithInt("Procedures", "procedureNumber", procNum);}
    }
	
	
	public boolean isInPaymentListEmpty(){
		//return paymentList.isEmpty();
        return this.db.isTableEmpty("Payments");
	}
	
	public void addPayment(double newPaymentAmt, Date newPaymentDate){
		//paymentList.add(new Payment(newPaymentAmt, newPaymentDate));
        this.db.addToPaymentTable(newPaymentAmt, newPaymentDate, this.getInvoiceNo());
	}
	
	public void removePayment(int payNum){
		if(this.db.isTableEmpty("Payments")){
			System.out.println("Invoice payment list is empty");
		}//else{paymentList.remove(index);}
        else{this.db.removeRowWithInt("Payments", "paymentNumber", payNum);}
	}
	
	public double getPaymentTotal(){
		double total = 0.0;
		for(int i = 0; i < this.getPaymentList().size(); i++){
			total += this.getPaymentList().get(i).getPaymentAmt();
		}
		return total;
	}
    
    public double getCostTotal(){
		double total = 0.0;
		for(int i = 0; i < this.getProcList().size(); i++){
			total += this.getProcList().get(i).getProcCost();
		}
		return total;
	}
    
    public String getPatientName(){
        String patient;
        patient = "get patient name on invoice";
        return patient;
    }
    
    public Payment getPayment(int index){
        return this.getPaymentList().get(index);
    }
    
    public Procedure getProcedure(int index){
        //return procList.get(index);
        return this.getProcList().get(index);
    }
    
    public Procedure findProcByNum(int index){
		Procedure proc = null;
		for(int i = 0; i < this.getProcList().size(); i++){
            if(index == this.getProcedure(i).getProcNo()){
				proc = this.getProcedure(i);
			}
		}
		return proc;
	}
    
    public int findProcIndex(int num){
        int index = -1;
        for(int i = 0; i < this.getProcList().size(); i++){
            if(num == this.getProcList().get(i).getProcNo()){
            	index =  i;
            }
        }
        return index;
    }
    
    public Payment findPayByNum(int index){
		Payment pay = null;
		for(int i = 0; i < this.getPaymentList().size(); i++){
            if(index == this.getPayment(i).getPaymentNo()){
				pay = this.getPayment(i);
			}
		}
		return pay;
	}
    
    public int findPayIndex(int num){
        int index = -1;
        for(int i = 0; i < this.getPaymentList().size(); i++){
            if(num == this.getPaymentList().get(i).getPaymentNo()){
            	index =  i;
            }
        }
        return index;
    }
    
    public Double getTotalPaid(){
        double paid = 0;
        for(int i = 0; i < this.getPaymentList().size(); i++){
            paid += this.getPaymentList().get(i).getPaymentAmt();
        }
        return paid;
    }
    
    public Double calcAmountRemaining(){
        double bill = 0;
        bill = this.getInvoiceAmt() - this.getTotalPaid();
        return bill;
    }
    
    public void paidCheck(){
        if(this.calcAmountRemaining() <= 0){
            this.setPaid(true);
        }else{this.setPaid(false);}
    }

	public String toString(){
		return 	"\nInvoice#\t: " + this.invoiceNo
				+ "\nDate\t\t: " + this.date.toString()
				+ "\nAmount\t\t: " + this.invoiceAmt
                + "\nRemaining\t: " + this.calcAmountRemaining()
				+ "\nPaid?\t\t  " + this.isPaid
				+ "\n\n";
	}
	
	public void print(){
		System.out.println(this);
	}

	
}
