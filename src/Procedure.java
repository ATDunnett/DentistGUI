
import java.io.Serializable;


public class Procedure implements Serializable{
	
	//private static int procNoCounter;
	private int procNo;
	private String procName;
	private double procCost;
    private DBConnection db;
	
	public Procedure(int procNum, DBConnection database){
        this.db = database;
        this.procNo = procNum;
		this.procName = this.db.getStringDataWithInt("procedureName", "Procedures", "procedureNumber", procNum);
		this.procCost = this.db.getDoubleWithInt("procedureCost", "Procedures", "procedureNumber", procNum);
        
		//this.procNo = Procedure.procNoCounter;
		//Procedure.procNoCounter++;
	}

	public int getProcNo() {
		return procNo;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public double getProcCost() {
		return procCost;
	}

	public void setProcCost(double procCost) {
		this.procCost = procCost;
	}
	
	public String toString(){
		return "Procedure#\t: " + this.procNo 
				+ "\nName\t\t: " + this.procName
				+ "\nCost\t\t: " + this.procCost
                + "\n\n";
	}
	
	public void print(){
		System.out.println(this);
	}

}
