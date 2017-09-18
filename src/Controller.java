import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Collections;
import java.util.List;

public class Controller {

    private DBConnection db;
    private Dentist selectedDentist;
    private Dentist loggedIn;
    private GUI gui;
    private Patient selectedPatient;
    private Invoice selectedInvoice;
    private Procedure selectedProcedure;
    private Payment selectedPayment;
    private ProcedureType selectedProcedureType;

    public Controller(DBConnection database) {
        this.db = database;
    }

    public Dentist getSelected() {
        return this.selectedDentist;
    }

    public void setSelected(String name) {
        this.selectedDentist = new Dentist(name, this.db);
    }

    public Dentist getLoggedIn() {
        return this.loggedIn;
    }

    public Patient getSelectedPatient() {
        return this.selectedPatient;
    }

    public void setSelectedPatient(Patient patient) {
        //this.selectedPatient = new Patient(name, this.db);
        this.selectedPatient = patient;
    }

    public void setGUI(GUI newGUI) {
        this.gui = newGUI;
    }

    //*****LogIn Scene methods*****
    public void populateLoginList(ComboBox<String> comboBoxLogin) {
        ArrayList<String> newList = this.db.getStringDataColumn("userName", "Dentists");
        for (int i = 0; i < newList.size(); i++) {
            comboBoxLogin.getItems().add(newList.get(i));
        }
    }

    public void loginSelect(String loginName) {
        this.selectForLogIn(loginName);
    }

    public void selectForLogIn(String dentistName) {
        ArrayList<String> newList = this.db.getStringDataColumn("userName", "Dentists");
        for (int i = 0; i < newList.size(); i++) {
            if (dentistName.equalsIgnoreCase(newList.get(i))) {
                this.setSelected(this.db.getStringData("userName", "Dentists", "userName", dentistName));
            }
        }
    }

    public Boolean attemptLogIn(String password) {
        Boolean verify = false;
        if (this.selectedDentist != null) {
            if (this.selectedDentist.getPassword().equals(password)) {
                verify = true;
                this.loggedIn = this.selectedDentist;
            } else {
                verify = false;
                this.loggedIn = null;
            }
        }
        return verify;
    }

    public void logIn(Stage primaryStage, Scene newScene, Scene oldScene, PasswordField password) {
        if (this.attemptLogIn(password.getText())) {
            this.gui.initScenePatient();
            this.gui.initScenePatientButtons();
            this.changeScene(primaryStage, newScene);
        } else {
            this.changeScene(primaryStage, oldScene);
        }
    }

    //*****Patient Scene methods*****
    public void populatePatientList(ListView<String> patientList) {
        ArrayList<String> newList = this.db.getStringDataColumnWithWhere("patientName", "Patients", "patientDentist", this.loggedIn.getName());
        this.clearViewList(patientList);
        for (int i = 0; i < newList.size(); i++) {
            patientList.getItems().add(newList.get(i));
        }
    }
    
    public void populatePatientListByNum(ListView<String> patientList) {
        ArrayList<String> newList = this.db.getStringDataColumnWithWhereSortNum("patientName", "Patients", "patientDentist", this.loggedIn.getName());
        this.clearViewList(patientList);
        for (int i = 0; i < newList.size(); i++) {
            patientList.getItems().add(newList.get(i));
        }
    }

    public void clearPatientList(ListView<String> patientList) {
        patientList.getItems().clear();
    }

    public void selectPatient(ListView<String> patientList, Text name, Text address, Text num, TextArea area, Text remain) {
        try {
            Patient newPat; 
            String patName = this.db.getStringData("patientName", "Patients", "patientName", patientList.getSelectionModel().getSelectedItem());
            if(patName.length() > 0){
                newPat = new Patient(patName, this.db);
            }else{newPat = null;}
            this.setSelectedPatient(newPat);
            populatePatientInfo(name, address, num, remain);
            area.setText(createInvoiceSummary());
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    public void goToInvoiceScene(Stage primaryStage, Scene newScene) {
        try {
            this.gui.initSceneInvoice();
            this.gui.initSceneInvoiceButtons();
            if (this.selectedPatient != null) {
                this.changeScene(primaryStage, newScene);
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }

    }

    public String createInvoiceSummary() {
        String summary = "";
        for (int i = 0; i < selectedPatient.getPatientInvoiceList().size(); i++) {
            summary += selectedPatient.getInvoice(i).toString();
        }
        return summary;
    }

    public void populatePatientInfo(Text name, Text address, Text num, Text remain) {
        Integer patNum = new Integer(this.selectedPatient.getPatientNumber());
        Double bill = new Double(this.selectedPatient.getRemainingBill());
        name.setText(selectedPatient.getName());
        address.setText(selectedPatient.getAddress());
        num.setText(patNum.toString());
        remain.setText(bill.toString());
    }

    public void addPatientDialog() {
        this.gui.initAddPatient();
    }

    public void addPatient(TextField newName, TextField newAddress) {
        String name, address;
        name = newName.getText();
        address = newAddress.getText();
        if (name.length() > 0) {
            this.loggedIn.addPatient(name, address);
        }
        this.gui.closePopUpStage();
        this.gui.initScenePatient();
    }

    public void removePatient() {
        try {
            this.loggedIn.removePatient(this.selectedPatient.getName());
            this.selectedPatient = null;
            this.gui.initScenePatient();
        } catch (NullPointerException e) {
            System.out.println(e);
        }

    }

    //*****Invoice Scene methods*****
    public void populateInvoiceList(ListView<String> invoiceList) {
        this.clearInvoiceList(invoiceList);
        if (this.selectedInvoice != null) {
            this.selectedInvoice.paidCheck();
        }
        for (int i = 0; i < this.selectedPatient.getPatientInvoiceList().size(); i++) {
            Integer invNum = new Integer(this.selectedPatient.getInvoice(i).getInvoiceNo());
            invoiceList.getItems().add(invNum.toString());
        }
    }

    public void clearInvoiceList(ListView<String> invoiceList) {
        invoiceList.getItems().clear();
    }

    public void selectInvoice(ListView<String> invoiceList, Text invNum, Text date, Text amount, Text paid, TextArea area, Text remain) {
        try {
            int searchInt = -1;
            try {
                searchInt = Integer.parseInt(invoiceList.getSelectionModel().getSelectedItem());
            } catch (NumberFormatException n) {
                System.out.println(n);
            }
            if (searchInt >= 0) {
                this.selectedInvoice = this.selectedPatient.findInvoiceByNum(searchInt);
                this.selectedInvoice.paidCheck();
                this.populateInvoiceInfo(invNum, date, amount, paid, remain);
                area.setText(this.createProcAndPaySummary());
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    public String createProcAndPaySummary() {
        String summary = "Procedures\n--------------\n";
        for (int i = 0; i < this.selectedInvoice.getProcList().size(); i++) {
            summary += this.selectedInvoice.getProcedure(i).toString();
        }
        summary += "\nPayments\n--------------\n";
        for (int i = 0; i < this.selectedInvoice.getPaymentList().size(); i++) {
            summary += this.selectedInvoice.getPayment(i).toString();
        }

        return summary;
    }

    public void populateInvoiceInfo(Text invNum, Text date, Text amount, Text paid, Text remain) {
        Integer num = new Integer(this.selectedInvoice.getInvoiceNo());
        Double money = new Double(this.selectedInvoice.getInvoiceAmt());
        Double bill = new Double(this.selectedInvoice.calcAmountRemaining());
        this.selectedInvoice.paidCheck();
        Boolean isPaid = this.selectedInvoice.isPaid();
        invNum.setText(num.toString());
        amount.setText(money.toString());
        date.setText(this.selectedInvoice.getInvoiceDate());
        paid.setText(isPaid.toString());
        remain.setText(bill.toString());
    }

    public void addInvoice() {
        Date today = new Date();
        Timestamp time = new Timestamp(today.getTime());
        this.selectedPatient.addInvoice(time);
        this.gui.initSceneInvoice();;
    }

    public void removeInvoice() {
        try {
            this.selectedPatient.removeInvoice(this.selectedInvoice.getInvoiceNo());
            this.selectedInvoice = null;
            this.gui.initSceneInvoice();
        } catch (NullPointerException e) {
            System.out.println(e);
        }

    }

    public void goToProcPayScene(Stage primaryStage, Scene newScene) {
        try {
            this.gui.initSceneProcPay();
            this.gui.initSceneProcPayButtons();
            if (this.selectedInvoice != null) {
                this.changeScene(primaryStage, newScene);
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }

    }

    //*****ProcPay Scene methods*****
    public void populateProcList(ListView<String> procList) {
        this.clearViewList(procList);
        for (int i = 0; i < this.selectedInvoice.getProcList().size(); i++) {
            Integer procNum = new Integer(this.selectedInvoice.getProcedure(i).getProcNo());
            procList.getItems().add(procNum.toString());
        }
    }

    public void populatePayList(ListView<String> payList) {
        this.clearViewList(payList);
        for (int i = 0; i < this.selectedInvoice.getPaymentList().size(); i++) {
            Integer payNum = new Integer(this.selectedInvoice.getPayment(i).getPaymentNo());
            payList.getItems().add(payNum.toString());
        }
    }

    public void selectProc(ListView<String> procList, TextArea area) {
        try {
            int searchInt = -1;
            try {
                searchInt = Integer.parseInt(procList.getSelectionModel().getSelectedItem());
            } catch (NumberFormatException n) {
                System.out.println(n);
            }
            if (searchInt >= 0) {
                this.selectedProcedure = this.selectedInvoice.findProcByNum(searchInt);
                area.setText(this.selectedProcedure.toString());
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    public void selectPay(ListView<String> payList, TextArea area) {
        try {
            int searchInt = -1;
            try {
                searchInt = Integer.parseInt(payList.getSelectionModel().getSelectedItem());
            } catch (NumberFormatException n) {
                System.out.println(n);
            }
            if (searchInt >= 0) {
                this.selectedPayment = this.selectedInvoice.findPayByNum(searchInt);
                area.setText(this.selectedPayment.toString());
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    public String createProcDetails() {
        String summary = "Procedures\n--------------\n";
        for (int i = 0; i < this.selectedInvoice.getProcList().size(); i++) {
            summary += this.selectedInvoice.getProcedure(i).toString();
        }
        return summary;
    }

    public String createPayDetails() {
        String summary = "Procedures\n--------------\n";
        for (int i = 0; i < this.selectedInvoice.getProcList().size(); i++) {
            summary += this.selectedInvoice.getProcedure(i).toString();
        }
        return summary;
    }

    public void populateProcedureTypeList(ArrayList<ProcedureType> list, ComboBox<ProcedureType> combobox) {
        for (int i = 0; i < list.size(); i++) {
            combobox.getItems().add(list.get(i));
        }
    }

    public void procedureTypeSelect(ProcedureType type) {
        this.selectedProcedureType = type;
    }
    
     public void addProcedureTypeDialog() {
        this.gui.initAddNewProcedure();
    }
    
    public void addProcedureType(TextField newName, TextField newCost) {
        String name;
        name = newName.getText();
        double cost = -1.0;
        try{
            cost = Double.parseDouble(newCost.getText());
            
        }catch(NumberFormatException e){
            System.out.println(e);
        }
        if (name.length() > 0 && cost > 0.0) {
            this.db.addToProcedureTypeTable(name, cost);
        }
        this.gui.closePopUpStage();
        this.gui.initScenePatient();
    }

    public void addProcedureDialog() {
        ArrayList<ProcedureType> list = new ArrayList<>();
        ArrayList<String> tempList1 = this.db.getStringDataColumn("typeName", "ProcedureTypes");
        ArrayList<Double> tempList2 = this.db.getDoubleDataColumn("typeCost", "ProcedureTypes");
        
        for(int i = 0; i < tempList1.size(); i++){
            list.add(new ProcedureType(tempList1.get(i),tempList2.get(i))); 
        }
        this.gui.initAddProcedure(list);
    }

    public void addProcedure() {
        try{
            this.selectedInvoice.addProcedure(this.selectedProcedureType);
        }catch(NullPointerException e){
            System.out.println(e);
        }
        
        this.gui.closePopUpStage();
        this.gui.initSceneProcPay();
    }

    public void removeProcedure() {
        try {
            this.selectedInvoice.removeProcedure(this.selectedProcedure.getProcNo());
            this.selectedProcedure = null;
            this.gui.initSceneProcPay();
        } catch (NullPointerException e) {
            System.out.println(e);
        }

    }

    public void addPaymentDialog() {
        this.gui.initAddPayment();
    }

    public void addPayment(TextField amount) {
        
        Date today = new Date();
        double payment = -1.0;
        try{
            payment = Double.parseDouble(amount.getText());
            
        }catch(NumberFormatException e){
            System.out.println(e);
        }
        
        if(payment > 0.0){
                this.selectedInvoice.addPayment(payment, today);
            }
        this.gui.closePopUpStage();
        this.gui.initSceneProcPay();
        amount.clear();
    }

    public void removePayment() {
        try {
            this.selectedInvoice.removePayment(this.selectedPayment.getPaymentNo());
            this.selectedPayment = null;
            this.gui.initSceneProcPay();
        } catch (NullPointerException e) {
            System.out.println(e);
        }

    }

    //*****Misc methods*****
    public String generateReport() {
        String report = "REPORT\n\n";
        ArrayList<Dentist> dentistList = new ArrayList<>();
        ArrayList<String> tempList = this.db.getStringDataColumn("userName", "Dentists");
        for(int i = 0; i < tempList.size(); i++){
            dentistList.add(new Dentist(tempList.get(i), this.db));
        }
                                          
        for(int i = 0; i < dentistList.size(); i++){
            report += dentistList.get(i).toString();    //dentists
            for(int j = 0; j < dentistList.get(i).getPatientList().size(); j++){
                report += dentistList.get(i).getPatient(j).toString();  //patients
                for(int k = 0; k < dentistList.get(i).getPatient(j).getPatientInvoiceList().size(); k++){
                    report += dentistList.get(i).getInvoice(j, k).toString();   //invoices
                    for(int l = 0; l < dentistList.get(i).getProcedureList(j, k).size(); l++){
                        report += dentistList.get(i).getProcedure(j, k, l).toString();
                    }
                    for(int h = 0; h < dentistList.get(i).getPaymentList(j, k).size(); h++){
                        report += dentistList.get(i).getPayment(j, k, h).toString();
                    }
                }
            }
        }
         
        return report;
    }

    public void reportToFile() {
        FileWriter output;
        String fileName = "report.txt", report = "";
        try {
            output = new FileWriter(fileName);
            report += this.generateReport();
            output.write(report);
            output.close();
        } catch (IOException k) {
            System.out.println(k);
        }
    }

    public void sortListView(ListView<String> list) {
        List<String> newList = list.getItems();
        Collections.sort((newList));
    }

    public void clearViewList(ListView<String> list) {
        list.getItems().clear();
    }

    public void changeScene(Stage currentStage, Scene newScene) {
        currentStage.setScene(newScene);
    }
    
    public void logOut(Stage primaryStage, Scene newScene) {
        this.loggedIn = null;
        this.selectedPatient = null;
        this.selectedInvoice = null;
        this.selectedProcedure = null;
        this.selectedPayment = null;
        this.gui.resetScenes();
        this.changeScene(primaryStage, newScene);
    }
    
    public void closePopUpStage() {
        this.gui.closePopUpStage();
    }

    public void exitProgram() {
        this.db.closeConnection();
        System.exit(0);
    }

}
