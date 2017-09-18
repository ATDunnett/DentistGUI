
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DentistList {
    private ArrayList<Dentist> list;
    private ObjectOutputStream output;
    private FileOutputStream fileOut;
    private ObjectInputStream input;
    private FileInputStream fileIn;
    
    public DentistList(){
        this.list = new ArrayList<>();
    }
    
    public ArrayList<Dentist> getList(){
        return this.list;
    }
    
    public Dentist getDentist(int index){
        return this.list.get(index);
    }
    
    public void adddfdDentist(String newName, String newAddress, String newPassword){
        //this.list.add(new Dentist(newName, newAddress, newPassword));
    }
    
    public void saveData(){
        for(int i = 0; i < list.size(); i++){
            try{
                this.fileOut = new FileOutputStream("dentist" + i + ".sav");
                this.output = new ObjectOutputStream(this.fileOut);
                this.output.writeObject(this.getDentist(i));
                this.output.close();
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }
    
    public void loadData(){
        Boolean filesToLoad = true;
        int counter = 0;
        while(filesToLoad){
            try{
                this.fileIn = new FileInputStream("dentist" + counter + ".sav");
                this.input = new ObjectInputStream(this.fileIn);
                try{
                    Dentist data = (Dentist)this.input.readObject();
                    this.list.add(data);
                }catch(ClassNotFoundException e){
                    System.out.println(e);
                   
                }
                counter++;
            }catch(IOException e){
                filesToLoad = false;
                System.out.println(e);
                
            }
        }
        if(this.list.size() == 0){
            this.loadBlankProfiles();
        }
    }
    
    public void loadBlankProfiles(){
        for(int i = 0; i < 3; i++){
            //this.list.add(new Dentist(("dentist0" + i), ("OLDaddress0" + i), "qwe"));
        }
        
    }
}
