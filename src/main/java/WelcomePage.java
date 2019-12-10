import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePage {
    private Pane pane;

    public WelcomePage() {
        pane = new VBox();
        pane.setPadding(new Insets(5, 5, 5, 5));
        Label label = new Label("Welcome to Koala's Management System");
        label.setFont(new Font("Arial", 20));
        label.setStyle("-fx-padding: 5");
        pane.getChildren().add(label);

        Button customerButton = new Button("Show Customers Information");
        pane.getChildren().add(customerButton);

        customerButton.setOnAction(event -> {
            ShowCustomerInformation showCustomerInformation = new ShowCustomerInformation();
            Singleton.getInstance().setObserver(showCustomerInformation);
            showCustomerInformation.show();
        });
    }

    public Pane getPane() {
        return pane;
    }
}
