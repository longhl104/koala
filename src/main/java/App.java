import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    private static final int APP_WIDTH = 450;
    private static final int APP_HEIGHT = 505;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Koala Company");
        stage.setResizable(false);
        Pane root = new Pane();

        root.getChildren().add(new WelcomePage().getPane());

        stage.setScene(new Scene(root, APP_WIDTH, APP_HEIGHT));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
