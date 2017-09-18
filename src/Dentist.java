import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

public class Dentist extends Person implements Serializable{
	
	private String password;
	//private ArrayList<Patient> patientList;
    //private ArrayList<ProcedureType> procedureTypeList;
    private DBConnection db;
    public Dentist(){
        super("---", "---");
        this.password = "---";
		//this.patientList = new ArrayList<>();
        //this.procedureTypeList = new ArrayList<>();
    }
	
	public Dentist(String newName, DBConnection database){
		super(newName, database.getStringData("userAddress", "Dentists", "userName", newName));
		this.password = database.getStringData("userPassword", "Dentists", "userName", newName);
        this.db = database;
		//this.patientList = new ArrayList<>();
        //this.procedureTypeList = new ArrayList<>();
        //this.procedureTypeList.add(new ProcedureType("procType00", 50.0));
        //this.procedureTypeList.add(new ProcedureType("procType01", 100.0));
        //this.procedureTypeList.add(new ProcedureType("procType02", 150.0));
        //this.procedureTypeList.add(new ProcedureType("procType03", 200.0));
        //this.procedureTypeList.add(new ProcedureType("procType04", 250.0));
        //this.procedureTypeList.add(new ProcedureType("procType05", 300.0));
        //this.procedureTypeList.add(new ProcedureType("procType06", 400.0));
        //this.procedureTypeList.add(new ProcedureType("procType07", 600.0));
        
	}
    
    public String getAddress(){
        return this.db.getStringData("userAddress", "Dentists", "userName", this.getName());
    }

	public String getPassword() {
		return this.db.getStringData("userPassword", "Dentists", "userName", this.getName());
	}
    
	public ArrayList<Patient> getPatientList() {
        ArrayList<Patient> patientList = new ArrayList<>();
        ArrayList<String> tempList = this.db.getStringDataColumnWithWhere("patientName", "Patients", "patientDentist", this.getName());
        for(int i = 0; i < this.db.getListSizeWithString("Patients", "patientDentist", this.getName()); i++){
            patientList.add(new Patient(tempList.get(i),this.db));
        }
		return patientList;
	}
    
	
	public boolean isPatientListEmpty(){
		//return patientList.isEmpty();
        return this.db.isTableEmpty("Patients");
	}
	
	public void addPatient(String newName, String newAddress){
		//patientList.add(new Patient(newName, newAddress));//OLD
        this.db.addToPatientTable(newName, newAddress, this.getName());
	}
    
    public void removePatient(String name){
		if(this.isPatientListEmpty()){
			System.out.println("Patient list is empty");
		}//else{patientList.remove(index);}
        else{this.db.removeRowWithString("Patients", "patientName", name);}
	}
    /*
    public void addInvoice(int patIndex, Date newDate){
        this.getPatient(patIndex).addInvoice(newDate);
    }
    */
    public void removeInvoice(int patIndex, int invIndex){
        this.getPatient(patIndex).removeInvoice(invIndex);
    }
    /*
    public void addProcedure(int patIndex, int invIndex, ProcedureType type){
        this.getInvoice(patIndex, invIndex).addProcedure(type);
    }
    
    public void removeProcedure(int patIndex, int invIndex, int procIndex){
       this.getInvoice(patIndex, invIndex).removeProcedure(procIndex);
    }
    
    public void addPayment(int patIndex, int invIndex, double newPaymentAmt, Date newPaymentDate){
        this.getInvoice(patIndex, invIndex).addPayment(newPaymentAmt, newPaymentDate);
    }
    
    public void removePayment(int patIndex, int invIndex, int payIndex){
        this.getInvoice(patIndex, invIndex).removePayment(payIndex);
    }
	*/
	
    
    public Patient getPatient(int patIndex){
        //String name = "", address = "";
        //name = this.db.getStringDataWithInt("patientName", "Patients", "patientNumber", patNum);
        //address = this.db.getStringDataWithInt("patientAddress", "Patients", "patientNumber", patNum);
        //Patient patient = new Patient(name, this.db);
        //return patient;
        return this.getPatientList().get(patIndex);
    }
    
    public Invoice getInvoice(int patNum, int invNum){
        return getPatient(patNum).getInvoice(invNum);
    }
    
    public Payment getPayment(int patIndex, int invIndex, int payIndex){
        return getPatient(patIndex).getInvoice(invIndex).getPayment(payIndex);
    }
    
    public Procedure getProcedure(int patIndex, int invIndex, int procIndex){
        return getPatient(patIndex).getInvoice(invIndex).getProcedure(procIndex);
    }
    
    public ArrayList<Invoice> getInvoiceList(int patIndex){
        return getPatient(patIndex).getPatientInvoiceList();
    }
    
    public ArrayList<Payment> getPaymentList(int patIndex, int invIndex){
        return getPatient(patIndex).getInvoice(invIndex).getPaymentList();
    }
    
    public ArrayList<Procedure> getProcedureList(int patIndex, int invIndex){
        return getPatient(patIndex).getInvoice(invIndex).getProcList();
    }
    /*
	public String patientNames(){
		String listOfNames = "";
		for(int i = 0; i < patientList.size(); i++){
			listOfNames += patientList.get(i).getName() + "\n";
		}
		return listOfNames;
	}
    
    public int findPatientNumber(String name){
        int patNum = -1;
        patNum = this.db.getIntData("patientNumber", "Patients", "patientName", name);
        return patNum;
    }
    
    public int findPatientIndex(String name){
        int index = -1;
        for(int i = 0; i < patientList.size(); i++){
            if(name.equalsIgnoreCase(this.getPatient(i).getName())){
            	index =  i;
            }
        }
        return index;
    }
    
    public Patient findPatientByName(String name){
        Patient patient = null;
        for(int i = 0; i < patientList.size(); i++){
            if(name.equalsIgnoreCase(this.getPatient(i).getName())){
            	patient = this.getPatient(i);
            }
        }
        if(patient == null){
        	System.out.println("Patient not in records");
        }
        return patient;
    }
    
    public Patient findPatientByName(String firstName, String surname){
        String fullName = firstName + " " + surname;
        Patient patient = null;
        for(int i = 0; i < patientList.size(); i++){
            if(fullName.equalsIgnoreCase(this.getPatient(i).getName())){
                patient = this.getPatient(i);
            }
        }
        if(patient == null){
        	System.out.println("Patient not in records");
        }
        return patient;
    }
    
    public Patient findPatientByNumber(int patNumber){
        Patient patient = null;
        for(int i = 0; i < patientList.size(); i++){
            if(patNumber == this.getPatient(i).getPatientNumber()){
                patient = this.getPatient(i);
            }
        }
        if(patient == null){
        	System.out.println("Patient not in records");
        }
        return patient;
    }
    */
    public Invoice findInvoiceByNumber(int patIndex, int invNumber){
        Invoice invoice = null;
        ArrayList<Invoice> list = this.getInvoiceList(patIndex);
        for(int i = 0; i < list.size(); i++){
            if(invNumber == list.get(i).getInvoiceNo()){
                invoice = list.get(i);
            }
        }
        return invoice;
    }
    
    public int findInvoiceIndex(int patIndex, int invNum){
        int index = -1;
        ArrayList<Invoice> list = this.getInvoiceList(patIndex);
        for(int i = 0; i < list.size(); i++){
            if(invNum == list.get(i).getInvoiceNo()){
            	index =  i;
            }
        }
        return index;
    }
    
    
    /*
    public double getPatientTotalBill(int patIndex){
        double endTotal, paymentTotal, costTotal;
        paymentTotal = this.getPatient(patIndex).getPaymentTotal();
        costTotal = this.getPatient(patIndex).getCostTotal();
        endTotal = costTotal - paymentTotal;
        return endTotal;            
    }
    */
	public String toString(){
		return  "\n************\n"
                + "Name\t: " + super.getName()
				+ "\nAddress\t: " + super.getAddress()
				//+ "\nPatient List\n------------\n"
				//+ patientNames()
                + "\n------------\n";
	}
	
	public void print(){
		System.out.println(this);
	}

}
