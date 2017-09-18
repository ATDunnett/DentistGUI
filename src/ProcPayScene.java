
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class ProcPayScene extends BaseSceneMain {
    //buttons
    private Button buttonAddProc;
    private Button buttonRemoveProc;
    private Button buttonAddPay;
    private Button buttonRemovePay;
    private Button buttonNewProc;
    //panes
    private VBox vBoxList;
    private VBox vBoxArea;
    //labels-text-lists
    private ListView<String> listProc;
    private ListView<String> listPay;
    private TextArea areaProc;
    private TextArea areaPay;
    private Label labelProcList;
    private Label labelPayList;
    private Label labelProcArea;
    private Label labelPayArea;
    //misc
	private Insets insets;

	public ProcPayScene(Controller newControl, int xSize, int ySize) {
		super(newControl, xSize, ySize);
        //assigning values
        this.buttonAddProc = new Button("Add Procedure");
        this.buttonRemoveProc = new Button("Remove Procedure");
        this.buttonAddPay = new Button("Add Payment");
        this.buttonRemovePay = new Button("Remove Payment");
        this.buttonNewProc = new Button("New Procedure");
        this.vBoxList = new VBox();
        this.vBoxArea = new VBox();
        this.listProc = new ListView<String>();
        this.listPay = new ListView<String>();
        this.areaProc = new TextArea();
        this.areaPay = new TextArea();
        this.labelProcList = new Label("Procedures :");
        this.labelPayList = new Label("Payments :");
        this.labelProcArea = new Label("Procedure Details");
        this.labelPayArea = new Label("Payment Details");
        super.getLabelTop().setText("Patient :");
 
        //populating panes
        super.getHBoxBottomLeft().getChildren().addAll(this.buttonAddProc, this.buttonRemoveProc, this.buttonNewProc);
        super.getHBoxBottomRight().getChildren().addAll(this.buttonAddPay, this.buttonRemovePay);
        this.vBoxList.getChildren().addAll(this.labelProcList, this.listProc, this.labelPayList, this.listPay);
        this.vBoxArea.getChildren().addAll(this.labelProcArea, this.areaProc, this.labelPayArea, this.areaPay);
        super.getMainPane().setLeft(this.vBoxList);
        super.getMainPane().setRight(this.vBoxArea);
        
        //setting parameters
        this.insets = new Insets(15);
		this.buttonAddProc.setPadding(insets);
        this.buttonRemoveProc.setPadding(insets);
        this.buttonAddPay.setPadding(insets);
        this.buttonRemovePay.setPadding(insets);
        this.buttonNewProc.setPadding(insets);
        this.vBoxList.setPadding(insets);
        this.vBoxArea.setPadding(insets);
        this.listProc.setPrefHeight(212);
        this.listProc.setPrefWidth(150);
        this.listPay.setPrefHeight(212);
        this.listPay.setPrefWidth(150);
        this.areaProc.setPrefHeight(212);
        this.areaProc.setPrefWidth(200);
        this.areaPay.setPrefHeight(212);
        this.areaPay.setPrefWidth(200);
        this.areaProc.setEditable(false);
        this.areaPay.setEditable(false);
        
        
	}

    @Override
    public void populateDetails() {
        try{
            super.getLabelTopName().setText(super.getControl().getSelectedPatient().getName());
            super.getControl().populateProcList(this.listProc);
            super.getControl().populatePayList(this.listPay);
            this.listProc.setOnMouseClicked(e -> {
                    super.getControl().selectProc(this.listProc, this.areaProc);
            });
            this.listPay.setOnMouseClicked(e -> {
                    super.getControl().selectPay(this.listPay, this.areaPay);
            });
        }catch(NullPointerException e){
            System.out.println("Null Pointer Exception :" + e.getMessage());
        }
        this.areaPay.clear();
        this.areaProc.clear();
    }

    @Override
    public void clearDetails() {
        
    }
    
    public void addProcedureButton(){
        this.buttonAddProc.setOnAction(e -> {
            super.getControl().addProcedureDialog();
        });
    }
    
    public void removeProcedureButton(){
        this.buttonRemoveProc.setOnAction(e -> {
            super.getControl().removeProcedure();
        });
    }
    
    public void addPaymentButton(){
        this.buttonAddPay.setOnAction(e -> {
            super.getControl().addPaymentDialog();
        });
    }
    
    public void removePaymentButton(){
        this.buttonRemovePay.setOnAction(e -> {
            super.getControl().removePayment();
        });
    }
    public void initNewButton(){
        this.buttonNewProc.setOnAction(e -> {
            super.getControl().addProcedureTypeDialog();
        });
    }
	
}
