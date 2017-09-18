import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

abstract public class BaseScenePopUp {
    private Scene scene;
    private BorderPane mainPane;
    private Controller control;

    public BaseScenePopUp(Controller newControl, int xSize, int ySize){
        this.control = newControl;
        this.mainPane = new BorderPane();
        this.scene = new Scene(this.mainPane, xSize, ySize);
    }

    public Controller getControl(){
        return control;
    }

    public BorderPane getMainPane(){
        return this.mainPane;
    }

    public Scene getScene(){
        return this.scene;
    }

}
