import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

abstract public class BaseSceneMain {

    private Scene scene;
    private Controller control;
    //buttons
    private Button buttonLogout;
    private Button buttonBack;
    private Button buttonReport;
    //panes
    private BorderPane mainPane;
    private VBox vBoxTop;
    private VBox vBoxBottom;
    private BorderPane borderPaneButtonsTop;
    private BorderPane borderPaneButtonsBottom;
    private HBox hBoxBottomLeft;
    private HBox hBoxBottomRight;
    private HBox hBoxTopLeft;
    private HBox hBoxTopRight;
    //labels-text-lists
    private Label labelTop;
    private Label labelTopName;
    //misc
    private Separator sepTop;
    private Separator sepBottom;
    private Insets insets1;

    public BaseSceneMain(Controller newControl, int xSize, int ySize) {
        this.control = newControl;
        //buttons
        this.buttonLogout = new Button("Logout");
        this.buttonBack = new Button("Back");
        this.buttonReport = new Button("Report");
        //panes
        this.mainPane = new BorderPane();
        this.vBoxTop = new VBox();
        this.vBoxBottom = new VBox();
        this.borderPaneButtonsTop = new BorderPane();
        this.borderPaneButtonsBottom = new BorderPane();
        this.hBoxBottomLeft = new HBox();
        this.hBoxBottomRight = new HBox();
        this.hBoxTopRight = new HBox();
        this.hBoxTopLeft = new HBox();
        //labels-text-lists
        this.labelTop = new Label("top");
        this.labelTopName = new Label("topMain");
        //misc
        this.sepTop = new Separator();
        this.sepBottom = new Separator();
        this.insets1 = new Insets(15);
        //scene
        this.scene = new Scene(this.mainPane, xSize, ySize);
	
        //populating panes
        this.mainPane.setTop(this.vBoxTop);
        this.mainPane.setBottom(this.vBoxBottom);
        this.vBoxTop.getChildren().addAll(this.borderPaneButtonsTop, this.sepTop);
        this.vBoxBottom.getChildren().addAll(this.sepBottom, this.borderPaneButtonsBottom);
        this.borderPaneButtonsTop.setLeft(this.hBoxTopLeft);
        this.borderPaneButtonsTop.setRight(this.hBoxTopRight);
        this.borderPaneButtonsBottom.setLeft(this.hBoxBottomLeft);
        this.borderPaneButtonsBottom.setRight(this.hBoxBottomRight);
        this.hBoxTopLeft.getChildren().addAll(this.labelTop, this.labelTopName);
        this.hBoxTopRight.getChildren().addAll(this.buttonBack, this.buttonReport, this.buttonLogout);
        this.hBoxBottomLeft.getChildren().addAll();
        this.hBoxTopRight.getChildren().addAll();
        
        //setting parameters
        this.labelTop.setPadding(insets1);
        this.labelTopName.setPadding(insets1);
        this.buttonLogout.setPadding(insets1);
        this.buttonBack.setPadding(insets1);
        this.buttonReport.setPadding(insets1);
		
    }
    
    public Controller getControl(){
        return control;
    }
    
    public Button getLogOutButton(){
        return this.buttonLogout;
    }
    
    public Button getBackButton(){
        return this.buttonBack;
    }
    
    public Button getReportButton(){
        return this.buttonReport;
    }
	
    public BorderPane getMainPane(){
        return this.mainPane;
    }
    
    public VBox getVBoxTop(){
        return this.vBoxTop;
    }
    
    public VBox getVBoxBottom(){
        return this.vBoxBottom;
    }
    
    public BorderPane getBorderPaneButtonsTop(){
        return this.borderPaneButtonsTop;
    }
    
    public BorderPane getBorderPaneButtonsBottom(){
        return this.borderPaneButtonsBottom;
    }
    
    public HBox getHBoxTopLeft(){
        return this.hBoxTopLeft;
    }
    
    public HBox getHBoxTopRight(){
        return this.hBoxTopRight;
    }
    
    public HBox getHBoxBottomLeft(){
        return this.hBoxBottomLeft;
    }
    
    public HBox getHBoxBottomRight(){
        return this.hBoxBottomRight;
    }
    
    public Label getLabelTop(){
        return this.labelTop;
    }
    
    public Label getLabelTopName(){
        return this.labelTopName;
    }
	
    public Scene getScene(){
        return this.scene;
    }
    
    abstract public void populateDetails();
    
    abstract public void clearDetails();
    
    public void logoutButton(Stage primaryStage, Scene newScene){
        this.buttonLogout.setOnAction(e -> {
            this.control.logOut(primaryStage, newScene);
        });
    }
    
    public void backButton(Stage primaryStage, Scene newScene){
    	this.buttonBack.setOnAction(e -> {
            this.control.changeScene(primaryStage, newScene);
    	});
    }
    
    public void reportButton(){
    	this.buttonReport.setOnAction(e -> {
            this.control.reportToFile();
    	});
    }

}
