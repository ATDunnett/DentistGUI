
import java.io.Serializable;

public class ProcedureType implements Serializable{
    private String name;
    private Double cost;
    
    public ProcedureType(String newName, Double newCost){
        this.name = newName;
        this.cost = newCost;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Double getCost(){
        return this.cost;
    }
    
    public String toString(){
        return "" + name + " " + cost;
    }
}
