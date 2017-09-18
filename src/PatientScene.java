import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PatientScene extends BaseSceneMain {
    //buttons
	private Button buttonAddPatient;
	private Button buttonRemovePatient;
	private Button buttonInvoicePage;
    private Button buttonSort;
    private Button buttonSortNum;
	//panes
	private GridPane gridPatientInfo;
	private VBox vBoxInvSum;
	private VBox vBoxPatientList;
	private VBox vBoxPatientDetails;
    private HBox hBoxSort;
	//labels-text-lists
	private TextArea tAreaInvSum;
	private Label labelPatientNum;
	private Label labelPatientName;
	private Label labelPatientAddress;
    private Label labelPatientBill;
	private Label labelSummary;
	private Label labelList;
	private Label labelInfo;
    private Label labelSortBy;
	private ListView<String> patientList;
    private Text patientName;
    private Text patientNum;
    private Text patientAddress;
    private Text patientBill;
	//misc
	private Insets insets;
	
	public PatientScene(Controller newControl, int xSize, int ySize){
        //assigning values
		super(newControl, xSize, ySize);	
        this.buttonAddPatient = new Button("Add Patient");
        this.buttonRemovePatient = new Button("Remove Patient");
        this.buttonInvoicePage = new Button("Edit Selected");
        this.buttonSort = new Button("Name");
        this.buttonSortNum = new Button("#");
        this.hBoxSort = new HBox();
        this.gridPatientInfo = new GridPane();
        this.vBoxInvSum = new VBox();
        this.vBoxPatientList = new VBox();
        this.vBoxPatientDetails = new VBox();
        this.tAreaInvSum = new TextArea();
        this.labelPatientNum = new Label("Patient # :");
        this.labelPatientName = new Label("Name :");
        this.labelPatientAddress = new Label("Address :");
        this.labelPatientBill = new Label("Remaining Bill :");
        super.getLabelTop().setText("Dentist :");
        this.labelSummary = new Label("Summary of Invoices");
        this.labelList = new Label("Patient List");
        this.labelInfo = new Label("Patient Information");
        this.labelSortBy = new Label("Sort by :");
        this.patientList = new ListView<>();
        this.insets = new Insets(15);
        this.patientName = new Text("---");
        this.patientNum = new Text("---");
        this.patientAddress = new Text("---");
        this.patientBill = new Text("---");
        
		//populating panes
        super.getHBoxBottomLeft().getChildren().addAll(this.buttonAddPatient, this.buttonRemovePatient);
        super.getHBoxBottomRight().getChildren().addAll(this.buttonInvoicePage);
        this.gridPatientInfo.getColumnConstraints().add(new ColumnConstraints(150));
        this.gridPatientInfo.add(labelPatientNum, 0, 0);
        this.gridPatientInfo.add(patientNum, 0, 1);
		this.gridPatientInfo.add(labelPatientName, 0, 2);
        this.gridPatientInfo.add(patientName, 0, 3);
		this.gridPatientInfo.add(labelPatientAddress, 0, 4);
        this.gridPatientInfo.add(patientAddress, 0, 5);
        this.gridPatientInfo.add(this.labelPatientBill, 0, 6);
        this.gridPatientInfo.add(this.patientBill, 0, 7);
        this.hBoxSort.getChildren().addAll(this.labelList, this.labelSortBy, this.buttonSort, this.buttonSortNum);
		this.vBoxPatientList.getChildren().addAll(this.hBoxSort, patientList);
		this.vBoxPatientDetails.getChildren().addAll(labelInfo, gridPatientInfo);
		this.vBoxInvSum.getChildren().addAll(labelSummary, tAreaInvSum);
		super.getMainPane().setLeft(vBoxPatientList);
		super.getMainPane().setCenter(vBoxPatientDetails);
		super.getMainPane().setRight(vBoxInvSum);
		//setting parameters
        super.getBackButton().setDisable(true);
		this.gridPatientInfo.setGridLinesVisible(false);
		this.gridPatientInfo.setVgap(20);
        this.gridPatientInfo.setPrefWidth(250);
		this.tAreaInvSum.setEditable(false);
        this.tAreaInvSum.setPrefHeight(405);
        this.tAreaInvSum.setPrefWidth(200);
		this.labelSummary.setPadding(insets);
		this.labelList.setPadding(insets);
		this.labelInfo.setPadding(insets);
		this.buttonAddPatient.setPadding(insets);
		this.buttonRemovePatient.setPadding(insets);
		this.buttonInvoicePage.setPadding(insets);
        this.buttonSort.setPadding(new Insets(0));
        this.buttonSortNum.setPadding(new Insets(0));
        //this.buttonSort.setTranslateX(38);
        this.buttonSort.setTranslateY(15);
        this.buttonSortNum.setTranslateY(15);
        this.labelSortBy.setTranslateY(15);
		this.patientList.setPrefWidth(150);
		this.patientList.setPrefHeight(405);
		VBox.setMargin(patientList, insets);
		VBox.setMargin(gridPatientInfo, insets);
		VBox.setMargin(tAreaInvSum, insets);		
	}
	@Override
	public void populateDetails(){
        try{
            super.getLabelTopName().setText(super.getControl().getLoggedIn().getName());  
            super.getControl().populatePatientList(this.patientList);
            patientList.setOnMouseClicked(e -> {
			super.getControl().selectPatient(patientList, patientName, patientAddress, patientNum, tAreaInvSum, this.patientBill);
		});
        }catch(NullPointerException e){
            System.out.println(e);
        }
        this.patientName.setText("---");
        this.patientNum.setText("---");
        this.patientAddress.setText("---");
        this.patientBill.setText("---");
        this.tAreaInvSum.clear();
	}
    @Override
    public void clearDetails(){
    	super.getControl().clearPatientList(this.patientList);
    }
    
    public void addPatientButton(){
    	this.buttonAddPatient.setOnAction(e -> {
    		super.getControl().addPatientDialog();
         });
    }
    
    public void removePatientButton(){
        this.buttonRemovePatient.setOnAction(e -> {
        	super.getControl().removePatient();
         });
    }
    
    public void invoiceSceneButton(Stage primaryStage, Scene newScene){
    	this.buttonInvoicePage.setOnAction(e -> {
    		super.getControl().goToInvoiceScene(primaryStage, newScene);
    	});
    }
    
    public void sortButton(){
        this.buttonSort.setOnAction(e -> {
            super.getControl().sortListView(this.patientList);
        });
    }
    
    public void sortButtonNum(){
        this.buttonSortNum.setOnAction(e -> {
            super.getControl().populatePatientListByNum(this.patientList);
        });
    }
    
    public ListView<String> getList(){
    	return patientList;
    }

}
