
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddProcedureTypeScene extends BaseScenePopUp {
    private VBox vBoxContents;
    private HBox hBoxName;
    private HBox hBoxAddress;
    private HBox hboxButtons;
    private Label labelName;
    private Label labelAddress;
    private TextField textName;
    private TextField textCost;
    private Button buttonAdd;
    private Button buttonCancel;
    private Insets inset1, inset2;

    public AddProcedureTypeScene(Controller control, int xSize, int ySize){
        super(control, xSize, ySize);
        this.vBoxContents = new VBox();
        this.hBoxName = new HBox();
        this.hBoxAddress = new HBox();
        this.hboxButtons = new HBox();
        this.labelName = new Label("Name :\t");
        this.labelAddress =  new Label("Cost :\t");
        this.textName = new TextField();
        this.textCost = new TextField();
        this.buttonAdd = new Button("Add");
        this.buttonCancel = new Button("Cancel");
        this.inset1 = new Insets(40);
        this.inset2 = new Insets(8);
        this.buttonAdd.setPadding(inset2);
        this.buttonAdd.setTranslateX(32);
        this.buttonAdd.setTranslateY(40);
        this.buttonCancel.setPadding(inset2);
        this.buttonCancel.setTranslateX(82);
        this.buttonCancel.setTranslateY(40);
        this.hBoxName.getChildren().setAll(this.labelName, this.textName);
        this.hBoxAddress.getChildren().setAll(this.labelAddress, this.textCost);
        this.hboxButtons.getChildren().setAll(this.buttonAdd, this.buttonCancel);
        this.vBoxContents.getChildren().setAll(this.hBoxName, this.hBoxAddress, this.hboxButtons);
        super.getMainPane().setCenter(this.vBoxContents);
        super.getMainPane().setPadding(this.inset1);
    }

    public void initAddButton(){
        this.buttonAdd.setOnAction(e -> {
        super.getControl().addProcedureType(textName, textCost);
        this.textName.clear();
        this.textCost.clear();
        });
    }

    public void initCancelButton(){
        this.buttonCancel.setOnAction(e -> {
        super.getControl().closePopUpStage();
        });
    }
}
