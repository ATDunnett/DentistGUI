import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InvoiceScene extends BaseSceneMain {
	//buttons
	private Button buttonAddInvoice;
	private Button buttonRemoveInvoice;
	private Button buttonProcPayPage;
	//panes
	private GridPane gridInvoiceInfo;
	private VBox vBoxProcPaySum;
	private VBox vBoxInvoiceList;
	private VBox vBoxInvoiceDetails;
	//labels/text/lists
	private TextArea tAreaSummary;
	private Label labelInvoiceNum;
	private Label labelInvoiceDate;
	private Label labelInvoiceAmount;
    private Label labelAmountRemianing;
    private Label labelInvoicePaid;
	private Label labelPatientName;
	private Label labelSummary;
	private Label labelList;
	private Label labelInfo;
	private Text textInvNum;
	private Text textInvDate;
	private Text textInvAmount;
    private Text textAmountRemaing;
    private Text textInvPaid;
	private ListView<String> invoiceList;
	//misc
	private Insets insets1;
	
	
	public InvoiceScene(Controller newControl, int xSize, int ySize){
		super(newControl, xSize, ySize);
		//buttons
		buttonAddInvoice = new Button("Add Invoice");
		buttonRemoveInvoice = new Button("Remove Invoice");
		buttonProcPayPage = new Button("Edit Selected");
		gridInvoiceInfo = new GridPane();
		vBoxProcPaySum = new VBox();
		vBoxInvoiceList = new VBox();
		vBoxInvoiceDetails = new VBox();
		//labels/text/lists
		tAreaSummary = new TextArea();
		labelInvoiceNum = new Label("Invoice # :");
		labelInvoiceDate = new Label("Invoice Date :");
		labelInvoiceAmount = new Label("Bill Amount :");
        this.labelAmountRemianing = new Label("Bill Remaining :");
        labelInvoicePaid = new Label("InvoicePaid :");
        super.getLabelTop().setText("Patient :");
		labelPatientName = new Label("temp");
		labelSummary = new Label("summary of proc/pay");
		labelList = new Label("Invoice list");
		labelInfo = new Label("Invoice Information");
		invoiceList = new ListView<>();
		textInvNum = new Text("---");
		textInvDate = new Text("---");
		textInvAmount = new Text("---");
        this.textAmountRemaing = new Text("---");
        textInvPaid = new Text("---");
        
		//misc
		insets1 = new Insets(15);
		//populating panes
        super.getHBoxBottomLeft().getChildren().addAll(this.buttonAddInvoice, this.buttonRemoveInvoice);
        super.getHBoxBottomRight().getChildren().addAll(this.buttonProcPayPage);
        this.gridInvoiceInfo.getColumnConstraints().add(new ColumnConstraints(150));
        //this.gridInvoiceInfo.getColumnConstraints().add(new ColumnConstraints(130));
		gridInvoiceInfo.add(labelInvoiceNum, 0, 0);
        gridInvoiceInfo.add(textInvNum, 0, 1);
		gridInvoiceInfo.add(labelInvoiceDate, 0, 2);
        gridInvoiceInfo.add(textInvDate, 0, 3);
		gridInvoiceInfo.add(labelInvoiceAmount, 0, 4);
        gridInvoiceInfo.add(textInvAmount, 0, 5);
        this.gridInvoiceInfo.add(this.labelAmountRemianing, 0, 6);
        this.gridInvoiceInfo.add(this.textAmountRemaing, 0, 7);
        gridInvoiceInfo.add(labelInvoicePaid, 0, 8);
		gridInvoiceInfo.add(textInvPaid, 0, 9);
		vBoxInvoiceList.getChildren().addAll(labelList, invoiceList);
		vBoxInvoiceDetails.getChildren().addAll(labelInfo, gridInvoiceInfo);
		vBoxProcPaySum.getChildren().addAll(labelSummary, tAreaSummary);				
		//setting parameters
		gridInvoiceInfo.setGridLinesVisible(false);
		//gridInvoiceInfo.setHgap(50);
		gridInvoiceInfo.setVgap(20);
        this.gridInvoiceInfo.setPrefWidth(250);
        //this.gridInvoiceInfo.set
		tAreaSummary.setEditable(false);
        this.tAreaSummary.setPrefHeight(405);
        this.tAreaSummary.setPrefWidth(200);
		labelPatientName.setPadding(insets1);
		labelSummary.setPadding(insets1);
		labelList.setPadding(insets1);
		labelInfo.setPadding(insets1);
		buttonAddInvoice.setPadding(insets1);
		buttonRemoveInvoice.setPadding(insets1);
		buttonProcPayPage.setPadding(insets1);
        invoiceList.setPrefWidth(150);
		invoiceList.setPrefHeight(405);
		VBox.setMargin(invoiceList, insets1);
		VBox.setMargin(gridInvoiceInfo, insets1);
		VBox.setMargin(tAreaSummary, insets1);
        super.getMainPane().setLeft(vBoxInvoiceList);
		super.getMainPane().setCenter(vBoxInvoiceDetails);
		super.getMainPane().setRight(vBoxProcPaySum);
	}
	
    @Override
	public void populateDetails(){
        try{
            super.getLabelTopName().setText(super.getControl().getSelectedPatient().getName());
            super.getControl().populateInvoiceList(this.invoiceList);
            invoiceList.setOnMouseClicked(e -> {
                super.getControl().selectInvoice(this.invoiceList, this.textInvNum, this.textInvDate, 
                    this.textInvAmount, this.textInvPaid, this.tAreaSummary, this.textAmountRemaing);
            });
        }catch(NullPointerException e){
            System.out.println("Null Pointer Exception :" + e.getMessage());
        }
		this.textInvNum.setText("---");
		this.textInvDate.setText("---");
		this.textInvAmount.setText("---");
        this.textAmountRemaing.setText("---");
        this.textInvPaid.setText("---");
        this.tAreaSummary.clear();
	}
	
    @Override
	public void clearDetails(){
		super.getControl().clearInvoiceList(this.invoiceList);
    }
	
	public void addInvoiceButton(){
    	this.buttonAddInvoice.setOnAction(e -> {
    		super.getControl().addInvoice();
         });
    }
    
    public void removeInvoiceButton(){
        this.buttonRemoveInvoice.setOnAction(e -> {
        	super.getControl().removeInvoice();
         });
    }
    
    public void procPaySceneButton(Stage primaryStage, Scene newScene){
        this.buttonProcPayPage.setOnAction(e -> {
            super.getControl().goToProcPayScene(primaryStage, newScene);
        });
    }
    
    public ListView<String> getList(){
    	return invoiceList;
    }

}
