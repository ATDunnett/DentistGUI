
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AddProcedureScene extends BaseScenePopUp{
    //buttons
    private Button buttonAdd;
    private Button buttonCancel;
    //panes
    private HBox hBoxMain;
    private HBox hBoxButtons;
    //lists misc
    private Label labelProc;
    private ObservableList<ProcedureType> proceduresList;
    private ComboBox<ProcedureType> comboBoxChoose;
    //misc
    private Insets insets;
    
    public AddProcedureScene(Controller newControl, int xSize, int ySize) {
        super(newControl, xSize, ySize);
        //buttons
        this.insets = new Insets(15);
        this.buttonAdd = new Button("Add");
        this.buttonCancel = new Button("Cancel");
        //panes
        this.hBoxMain = new HBox();
        this.hBoxButtons = new HBox();
        //lists misc
        this.labelProc = new Label("Procedure : ");
        this.proceduresList = FXCollections.observableArrayList();
        this.comboBoxChoose = new ComboBox<>(proceduresList);
        //populate panes
        this.hBoxMain.getChildren().addAll(this.labelProc, this.comboBoxChoose);
        this.hBoxButtons.getChildren().addAll(this.buttonAdd, this.buttonCancel);
        super.getMainPane().setCenter(this.hBoxMain);
        super.getMainPane().setBottom(this.hBoxButtons);
        //setting parameters
        this.hBoxMain.setTranslateY(40);
        this.hBoxMain.setTranslateX(45);
        this.hBoxButtons.setTranslateY(-40);
        this.hBoxButtons.setTranslateX(75);
        this.buttonCancel.setTranslateX(50);
        this.buttonAdd.setPadding(insets);
        this.buttonCancel.setPadding(insets);
    }
    
    public void initAddButton(){
        this.buttonAdd.setOnAction(e -> {
            super.getControl().addProcedure();
        });
    }
    
    public void initCancelButton(){
        this.buttonCancel.setOnAction(e -> {
            super.getControl().closePopUpStage();
        });
    }
    
    public void populateComboBox(ArrayList<ProcedureType> list){
        this.comboBoxChoose.getItems().clear();
        super.getControl().populateProcedureTypeList(list, this.comboBoxChoose);
    }
    
    public void comboBoxSelect(){
        comboBoxChoose.setOnAction( e -> {
            super.getControl().procedureTypeSelect(this.comboBoxChoose.getValue());
        });
    }
}
