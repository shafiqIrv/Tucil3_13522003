import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpage.fxml"));
        Parent root = loader.load();

        // Retrieve the controller instance from the FXMLLoader
        Controller controller = loader.getController();

        // Set up the primary stage
        // controller.setMainWindow(primaryStage);
        primaryStage.setTitle("Word Ladder");
        primaryStage.setScene(new Scene(root, 600, 600)); // Adjust the scene size as needed
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
