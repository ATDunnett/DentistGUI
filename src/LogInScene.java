
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class LogInScene extends BaseScenePopUp {
    private Button buttonLogin;
    private Button buttonExit;
    private VBox vboxLogin;
    private HBox hboxLoginName;
    private HBox hboxLoginPassword;
    private HBox hBoxButtons;
    private Label labelLoginName;
    private Label labeLoginPassword ;
    private ObservableList<String> dentistLogins;
    private ComboBox<String> comboBoxLogin;
    private PasswordField password;
    private Insets insets1;
    
    public LogInScene(Controller newControl, int xSize, int ySize){
        //assigning values
    	super(newControl, xSize, ySize);
        buttonLogin = new Button("Login");
        buttonExit = new Button("Exit");
        vboxLogin = new VBox();
        hboxLoginName = new HBox();
        hboxLoginPassword = new HBox();
        hBoxButtons = new HBox();
        labelLoginName = new Label("Name :\t\t");
        labeLoginPassword = new Label("Password :\t");
    	dentistLogins = FXCollections.observableArrayList();
        comboBoxLogin = new ComboBox<>(dentistLogins);
        password = new PasswordField();
        insets1 = new Insets(15);
        hBoxButtons.getChildren().addAll(buttonLogin, buttonExit);
        vboxLogin.getChildren().addAll(hboxLoginName,hboxLoginPassword,hBoxButtons); 
        hboxLoginName.getChildren().addAll(labelLoginName,comboBoxLogin);
        hboxLoginPassword.getChildren().addAll(labeLoginPassword,password);
        buttonLogin.setPadding(insets1);
        buttonExit.setPadding(insets1);
        labelLoginName.setPadding(insets1);
        labeLoginPassword.setPadding(insets1);
        comboBoxLogin.setTranslateY(10);
        this.buttonLogin.setPrefWidth(75);
        this.buttonExit.setPrefWidth(75);
        this.buttonLogin.setTranslateX(47);
        this.buttonExit.setTranslateX(110);
        comboBoxLogin.setPrefWidth(150);
        this.password.setPrefWidth(150);
        this.password.setTranslateY(11);
        super.getMainPane().setCenter(vboxLogin);
    }
    
    public void populateComboBox(){
        super.getControl().populateLoginList(this.comboBoxLogin);
    }
    
    public void comboBoxSelect(){
        comboBoxLogin.setOnAction( e -> {
        	super.getControl().loginSelect(this.comboBoxLogin.getValue());
		});
    }
    public void initLoginButton(Stage primaryStage, Scene newScene){
        this.buttonLogin.setOnAction(e -> {
        	super.getControl().logIn(primaryStage, newScene, super.getScene(), this.password);
        });
        
    }
    public void initExitButton(){
        this.buttonExit.setOnAction(e -> {
        	super.getControl().exitProgram();
        });
        
    }
    
    public void reset(){
        this.password.clear();
    }
    
    public String getEnteredPassword(){
        return this.password.getText();
    }
    
}
