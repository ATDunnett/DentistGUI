import javafx.application.Application;
import javafx.stage.Stage;
public class MainProgram extends Application{

    @Override
    public void start(Stage primaryStage){
        DBConnection db = new DBConnection("root","root");
        Controller control = new Controller(db);
        GUI gui = new GUI(control, primaryStage);
        control.setGUI(gui);
        gui.initSceneLogin();
        gui.initSceneLoginButtons();
        gui.initGUI();
    }
    public static void main(String[] args) {
        launch(args);
    }
}